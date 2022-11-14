package com.ithawk.demo.quartz.standalone.calendar;

import com.ithawk.demo.quartz.standalone.job.MyJob1;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>
 * 排除
 * </p>
 * @Author:
 * @Date:
 * @Description:
 */
public class CalendarDemo {
    public static void main(String[] args) throws Exception {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        scheduler.start();

        // 定义日历
        AnnualCalendar holidays = new AnnualCalendar();

        // 排除日
        Calendar testDay = (Calendar) new GregorianCalendar(2019, 8, 8);
        holidays.setDayExcluded(testDay, true);
        // 排除中秋节
        Calendar midAutumn = new GregorianCalendar(2019, 9, 13);
        holidays.setDayExcluded(midAutumn, true);
        // 排除圣诞节
        Calendar christmas = new GregorianCalendar(2019, 12, 25);
        holidays.setDayExcluded(christmas, true);

        // 调度器添加日历
        scheduler.addCalendar("holidays", holidays, false, false);

        JobDetail jobDetail = JobBuilder.newJob(MyJob1.class)
                .withIdentity("job1", "test1")
                .usingJobData("test","test 2673")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "test1")
                .startNow()
                .modifiedByCalendar("holidays")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();

        Date firstRunTime = scheduler.scheduleJob(jobDetail, trigger);
        System.out.println(jobDetail.getKey() + " 第一次触发： " + firstRunTime);
    }
}
