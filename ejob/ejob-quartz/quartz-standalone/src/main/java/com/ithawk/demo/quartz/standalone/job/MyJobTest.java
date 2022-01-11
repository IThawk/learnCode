package com.ithawk.demo.quartz.standalone.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author ithawk
 * @projectName ejob
 * @description: TODO
 * @date 2022/1/119:14
 */
public class MyJobTest {
    public static void main(String[] args) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(MyJob1.class)
                .withIdentity("job1", "group1")
                .usingJobData("test","2673")
                .usingJobData("moon",5.21F)
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }
}
