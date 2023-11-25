package com.xxl.job.http.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;
import java.util.concurrent.*;


@Component("xxlScheduledThreadPoolExecutorTools")
public class ScheduledThreadPoolExecutorTools {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledThreadPoolExecutorTools.class);

    @Autowired
    @Qualifier("xxlJobScheduledExecutorThreadPoolExecutor")
    private ScheduledThreadPoolExecutor executorService;

    @PreDestroy
    public void destroy() {

        if (Objects.nonNull(executorService) && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    @Async("xxlJobScheduledThreadPoolExecutor")
    public void scheduleCaller(ScheduleScheduleService scheduleScheduleService, long delay, int maxTime, int times) throws InterruptedException, ExecutionException {
        if (times == maxTime) {
            logger.warn("延迟推送 {} 超过次数 {}", scheduleScheduleService.getOthSerialNo(), times);
            scheduleScheduleService.doFinalScheduleDelay();
            return;
        }
        //进行首次处理
        if (0 == times) {
            scheduleScheduleService.doFirstScheduleDelay();
        }
        ScheduledFuture<ScheduleResultResponse> result = executorService.schedule(new Callable<ScheduleResultResponse>() {
            @Override
            public ScheduleResultResponse call() {
                return scheduleScheduleService.doScheduleDelay();
            }

        }, delay * times, TimeUnit.MINUTES);
        if (ScheduleResultResponse.SUCCESS_CODE.equals(result.get().getCode())) {
            return;
        }
        logger.warn("延迟推送数据 {} 次失败 {} 再次推送", times + 1, scheduleScheduleService.getOthSerialNo());
        times = times + 1;
        scheduleCaller(scheduleScheduleService, delay, maxTime, times);

    }
}
