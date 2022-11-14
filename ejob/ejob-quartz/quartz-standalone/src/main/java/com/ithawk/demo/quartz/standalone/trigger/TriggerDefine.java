package com.ithawk.demo.quartz.standalone.trigger;

import org.quartz.*;

/**
 * @Author:
 * @Date:
 * @Description:
 */
public class TriggerDefine {
    public static void main(String[] args) {

        /**
         * CalendarIntervalTrigger
         */
        Trigger calendarIntervalTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule()
                        .withIntervalInDays(1)) // 每天执行一次
                .build();


        /**
         * DailyTimeIntervalTrigger
         */
        Trigger dailyTimeIntervalTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                        .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(9, 0)) //第天9：00开始
                        .endingDailyAt(TimeOfDay.hourAndMinuteOfDay(16, 0)) //16：00 结束
                        .onDaysOfTheWeek(1,2,3,4,5) //周一至周五执行
                        .withIntervalInHours(1) //每间隔1小时执行一次
                        .withRepeatCount(100))//最多重复100次（实际执行100+1次）
                        .build();





    }
}
