package com.ithawk.demo.quartz.spring.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;

/**
 * @Author: qingshan
 * @Date: 2019/9/5 17:19
 * @Description: 咕泡学院，只为更好的你
 */
// @Configuration
public class QuartzConfig {
    @Bean
    public JobDetail printTimeJobDetail(){
        return JobBuilder.newJob(MyJob1.class)
                .withIdentity("gupaoJob")
                .usingJobData("gupao", "职位更好的你")
                .storeDurably()
                .build();
    }
    @Bean
    public Trigger printTimeJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(printTimeJobDetail())
                .withIdentity("quartzTaskService")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
