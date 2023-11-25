package com.xxl.job.http.handler;

import com.xxl.job.http.config.XxlJobBean;
import com.xxl.job.http.config.XxlJobHttpProperties;
import com.xxl.job.http.model.XxlJobLog;
import com.xxl.job.http.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import com.xxl.job.http.util.ReturnT;

/**
 * annotation for job handler
 *
 * @author 2023-5-17 21:06:49
 */
@RestController
@RequestMapping("/xxlJob/")
public class JobDefaultController implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(JobDefaultController.class);
    private ApplicationContext applicationContext;

    @Autowired
    private XxlJobHttpProperties xxlJobHttpProperties;

    @Autowired
    private ScheduledThreadPoolExecutorTools scheduledThreadPoolExecutorTools;

    @RequestMapping("/{taskName}/{taskMethod}")
    public String executeTask(@PathVariable String taskName, @PathVariable(required = false) String taskMethod,
                              @RequestParam(value = "logId", required = false) String logId,
                              @RequestBody(required = false) Map<String, String> reqMap,

                              HttpServletRequest httpServletRequest) throws ExecutionException, InterruptedException {
        String ip = "";
        try {
            ip = getIpAddress(httpServletRequest);
            //进行密钥 防止非法调用
            String key = StringUtils.isNotBlank(xxlJobHttpProperties.getKey()) ? xxlJobHttpProperties.getKey() : EncryptUtils.ECB_EKY_INNER;
            logId = EncryptUtils.sm4EcbDecrypt(key, logId);
            if (StringUtils.isBlank(logId)) {
                return "非法调用";
            }
            //限定执行器的机器防止其他调用
            if (!xxlJobHttpProperties.getExecutorIp().contains(ip)) {
                return "非法调用";
            }
        } catch (Exception e) {
            return "调用任务失败：" + ExceptionUtils.getStackTrace(e);
        }
        String finalLogId = logId;
        String finalIp = ip;
        scheduledThreadPoolExecutorTools.scheduleCaller(
                new AbstractScheduleScheduleService() {
                    @Override
                    public ScheduleResultResponse doScheduleDelay() {
                        ReturnT result = null;
                        int handleCode = ReturnT.FAIL_CODE;
                        try {
                            Object taskBean = applicationContext.getBean(taskName);
                            if (Objects.isNull(taskBean)) {
                                return new ScheduleResultResponse();
                            }

                            if (!taskBean.getClass().isAnnotationPresent(XxlJobBean.class)
                                    && !taskBean.getClass().isAnnotationPresent(EnableScheduling.class)) {
                                return new ScheduleResultResponse();
                            }
                            if (Objects.isNull(reqMap)) {
                                Method method = taskBean.getClass().getMethod(taskMethod);
                                ReflectionUtils.makeAccessible(method);
                                result = (ReturnT) method.invoke(taskBean);
                            } else {
                                Method method = taskBean.getClass().getMethod(taskMethod, Map.class);
                                ReflectionUtils.makeAccessible(method);
                                result = (ReturnT) method.invoke(taskBean, reqMap);
                            }

                            handleCode = ReturnT.SUCCESS_CODE;
                        } catch (NoSuchMethodException e) {
                            result = new ReturnT<>("定时任务请求的定时方法不存在!");
                            logger.error("定时任务请求的定时方法不存在:{}", ExceptionUtils.getStackTrace(e));
                            result.setCode(ReturnT.FAIL_CODE);
                        } catch (IllegalAccessException e) {
                            result = new ReturnT<>("定时任务请求的定时方法无法反射执行!" + ExceptionUtils.getStackTrace(e));
                            logger.error("定时任务请求的定时方法无法反射执行:{}", ExceptionUtils.getStackTrace(e));
                            result.setCode(ReturnT.FAIL_CODE);
                        } catch (InvocationTargetException e) {
                            result = new ReturnT<>("定时任务请求的定时方法反射执行失败!");
                            logger.error("定时任务请求的定时方法反射执行失败:{}", ExceptionUtils.getStackTrace(e));
                            result.setCode(ReturnT.FAIL_CODE);
                        } catch (Exception e) {
                            result = new ReturnT<>("定时任务请求的定时方法执行失败!" + ExceptionUtils.getStackTrace(e));
                            logger.error("定时任务请求的定时方法执行失败:{}", ExceptionUtils.getStackTrace(e));
                            result.setCode(ReturnT.FAIL_CODE);
                        } finally {
                            addExecuteLog(result.getContent(), finalLogId, handleCode, finalIp);
                        }
                        return new ScheduleResultResponse();
                    }

                    @Override
                    public String getOthSerialNo() {
                        return null;
                    }
                }, 0, 2, 1);
        return "调用任务完成";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    public void addExecuteLog(Object result, String logId, int handleCode, String ip) {
        if (StringUtils.isEmpty(logId)) {
            return;
        }
        try {
            Object finalResult = result;
            scheduledThreadPoolExecutorTools.scheduleCaller(
                    new AbstractScheduleScheduleService() {
                        @Override
                        public ScheduleResultResponse doScheduleDelay() {
                            //添加到调用日志中，方便查询
                            XxlJobLog xxlJobLog = new XxlJobLog();
                            xxlJobLog.setId(Long.valueOf(logId));
                            xxlJobLog.setHandleCode(handleCode);
                            try {

                                // 5、collection handler info
                                StringBuffer triggerMsgSb = new StringBuffer();
                                String hostIp = InetAddress.getLocalHost().getHostAddress();
                                triggerMsgSb.append("<br>").append("定时任务执行机器：").append("：").append(ip);
                                triggerMsgSb.append("<br>").append("调用地址：").append("：").append(hostIp);
                                if (result instanceof String) {
                                    triggerMsgSb.append("<br>").append("定时任务执行返回：").append("：").append(finalResult);
                                } else {
                                    triggerMsgSb.append("<br>").append("定时任务执行返回：").append("：").append(finalResult);
                                }
                                String triggerMsg = triggerMsgSb.toString();
                                xxlJobLog.setHandleMsg(triggerMsgSb.toString());
                                ReturnT<String> returnT = XxlJobRemotingUtil.postBody(
                                        xxlJobHttpProperties.getJobWebUrl() + "/joblog/logAddHandlerMsg",
                                        "", 5000, xxlJobLog, String.class);


                            } catch (Exception e) {
                                logger.error("定时任务添加回调日志执行失败:{}", ExceptionUtils.getStackTrace(e));
                                xxlJobLog.setHandleCode(ReturnT.FAIL_CODE);
                                xxlJobLog.setHandleMsg(ExceptionUtils.getStackTrace(e));
                                ReturnT<String> returnT = XxlJobRemotingUtil.postBody(
                                        xxlJobHttpProperties.getJobWebUrl() + "/joblog/logAddHandlerMsg",
                                        "", 5000, xxlJobLog, String.class);
                            }
                            return new ScheduleResultResponse();
                        }

                        @Override
                        public String getOthSerialNo() {
                            return null;
                        }
                    }
                    , 1, 2, 1);
        } catch (InterruptedException e) {
            logger.error("定时任务添加回调日志执行失败:{}", ExceptionUtils.getStackTrace(e));
        } catch (ExecutionException e) {
            logger.error("定时任务添加回调日志执行失败:{}", ExceptionUtils.getStackTrace(e));
        }
    }
}
