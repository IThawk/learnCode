package com.xxl.job.http.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;


@Configuration
public class ScheduledThreadPoolExecutorConfig {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledThreadPoolExecutor.class);


    @Bean("xxlJobScheduledThreadPoolExecutor")
    public ScheduledThreadPoolExecutor newScheduledThreadPoolExecutor() {
        return new java.util.concurrent.ScheduledThreadPoolExecutor(5,
                Executors.defaultThreadFactory(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                logger.warn("XXLjOB线程池一已经满了");
            }
        });
    }

    @Bean("xxlJobScheduledExecutorThreadPoolExecutor")
    public ScheduledThreadPoolExecutor newCxlJobScheduledExecutorThreadPoolExecutor() {
        return new java.util.concurrent.ScheduledThreadPoolExecutor(10,
                Executors.defaultThreadFactory(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                logger.warn("XXLjOB线程池二已经满了");
            }
        });
    }


}
