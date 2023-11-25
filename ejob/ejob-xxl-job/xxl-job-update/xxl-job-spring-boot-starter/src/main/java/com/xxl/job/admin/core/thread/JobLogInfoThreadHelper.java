package com.xxl.job.admin.core.thread;

import com.xxl.job.admin.core.conf.XxlJobAdminConfig;
import com.xxl.job.admin.core.model.XxlJobLog;
import com.xxl.job.admin.core.scheduler.XxlJobScheduler;
import com.xxl.job.core.biz.ExecutorBiz;
import com.xxl.job.core.biz.model.LogParam;
import com.xxl.job.core.biz.model.LogResult;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.log.XxlJobFileAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class JobLogInfoThreadHelper {
    private static Logger logger = LoggerFactory.getLogger(JobLogInfoThreadHelper.class);
    private static LinkedBlockingQueue<XxlJobLog> jobLogQueue = new LinkedBlockingQueue<>();

    private static JobLogInfoThreadHelper instance = new JobLogInfoThreadHelper();
    private volatile boolean toStop = false;

    public static JobLogInfoThreadHelper getInstance() {
        return instance;
    }

    /**
     * new trigger to queue
     *
     * @param triggerParam
     * @return
     */
    public static ReturnT<String> pushLogInfoQueue(XxlJobLog triggerParam) {
        jobLogQueue.add(triggerParam);
        return ReturnT.SUCCESS;
    }

    private Thread jobLogThread;


    public void start() {
        jobLogThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!toStop) {
                    try {
                        List<XxlJobLog> list = new ArrayList<>();//创建一个List集合
                        jobLogQueue.drainTo(list, 5000);


                        Map<Integer, List<XxlJobLog>> listMap = new HashMap<>();
                        for (int i = 0; i < list.size(); i++) {
                            int index = i % 4;
                            if (listMap.containsKey(index)) {
                                listMap.get(index).add(list.get(i));
                            } else {
                                List<XxlJobLog> list1 = new ArrayList<>(list.size() / 4 + 1);
                                list1.add(list.get(i));
                                listMap.put(index, list1);
                            }

                        }
                        Arrays.asList(0, 1, 2, 3).parallelStream().forEach(i -> {
                            if (CollectionUtils.isEmpty(listMap.get(i))) {
                                return;
                            }
                            List<XxlJobLog> pushList = new ArrayList<>(66);
                            AtomicInteger size = new AtomicInteger(0);
                            listMap.get(i).forEach(jobLog -> {
                                size.getAndIncrement();

                                XxlJobLog xxlJobLogjobLog = XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().load(jobLog.getId());
                                // appendlog
//                                String logFileName = XxlJobFileAppender.contextHolder.get();
                                String logFileName = XxlJobFileAppender.makeLogFileName(xxlJobLogjobLog.getHandleTime(), xxlJobLogjobLog.getId());
                                File logFile = new File(logFileName);

                                if (logFile.exists()) {
                                    if (logFileName != null && logFileName.trim().length() > 0) {
                                        XxlJobFileAppender.appendLog(logFileName, "-----------记录业务任务执行日志开始");
                                        XxlJobFileAppender.appendLog(logFileName, jobLog.getHandleMsg());
                                        XxlJobFileAppender.appendLog(logFileName, "\n-----------记录业务任务执行日志结束");
                                    }
                                } else {
                                    ExecutorBiz executorBiz = null;
                                    try {
                                        executorBiz = XxlJobScheduler.getExecutorBiz(xxlJobLogjobLog.getExecutorAddress());
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                    LogParam logParam = new LogParam(xxlJobLogjobLog.getHandleTime().getTime(), xxlJobLogjobLog.getId(), 0);
                                    logParam.setContent(jobLog.getHandleMsg());
                                    ReturnT<String> logResult = executorBiz.appendLog(logParam);
                                }
                                if (Objects.nonNull(xxlJobLogjobLog)) {
                                    String logS = jobLog.getHandleMsg().length() > 74 ? jobLog.getHandleMsg().substring(0, 74) : jobLog.getHandleMsg();
                                    xxlJobLogjobLog.setHandleMsg(logS);
                                    xxlJobLogjobLog.setHandleTime(new Date());
                                    xxlJobLogjobLog.setHandleCode(jobLog.getHandleCode());

                                }
                                pushList.add(xxlJobLogjobLog);
                                if (size.get() == 50) {
                                    size.set(0);
                                    XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().updateHandleInfoList(pushList);
                                    pushList.clear();
                                }

                            });
                            XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().updateHandleInfoList(pushList);
                            pushList.clear();
                        });


                        try {
                            TimeUnit.SECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            if (!toStop) {
                                logger.error(">>>>>>>>>>> xxl-job, JobLogInfoThreadHelper registry monitor thread error:{}", e);
                            }
                        }

                    } catch (Exception e) {
                        logger.error(">>>>>>>>>>> xxl-job, job JobLogInfoThreadHelper monitor thread error:{}", e);
                    }
                }
            }
        });
        jobLogThread.setDaemon(true);
        jobLogThread.setName("xxl-job, admin JobLogInfoThreadHelper#scheduleThread");
        jobLogThread.start();
        logger.info(">>>>>>>>>>> xxl-job JobLogInfoThreadHelper stoped, hashCode:{}", Thread.currentThread());
    }

    public void toStop() {
        toStop = true;
        // interrupt and wait
        jobLogThread.interrupt();
        try {
            jobLogThread.join();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
