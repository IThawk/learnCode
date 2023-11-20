package com.ithawk.demo.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;

@Component
public class CommonTimingTask implements SchedulingConfigurer  {

    @Bean
    public ThreadPoolTaskScheduler trPoolTaskScheduler() {
        ThreadPoolTaskScheduler a = new ThreadPoolTaskScheduler();
        a.setPoolSize(2);
        return a;

    }


    @Bean("dynamicScheduledTaskRegistrar")
    public DynamicScheduledTaskRegistrar dynamicScheduledTaskRegistrar() {
        DynamicScheduledTaskRegistrar scheduledTaskRegistrar = new DynamicScheduledTaskRegistrar();
        return scheduledTaskRegistrar;

    }

    //用于设置多线程
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(new ScheduledThreadPoolExecutor(4));
    }
}