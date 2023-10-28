package com.ithawk.demo.thread;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Configuration // 1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling // 2.开启定时任务
public class Task2 implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        try {
            scheduledTaskRegistrar.addTriggerTask(() -> {
                System.out.println(new Date() + "222222222222222222");
                try {
                    Thread.sleep(60000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, triggerContext -> {
                /**
                 * 2.设置执行周期(Trigger)
                 */
                // 2.1 从数据库获取执行周期
                String cron = "1/5 * * * * *";
                // 2.3 返回执行周期(Date)
                return new CronTrigger(cron).nextExecutionTime(triggerContext);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
