package com.ithawk.demo.quartz.standalone.listener;

import com.ithawk.demo.quartz.standalone.job.MyJob1;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;

/**
 * 测试监听器
 */
public class MyTriggerListenerTest {
	public static void main(String[] args) throws SchedulerException {

		// JobDetail
		JobDetail jobDetail = JobBuilder.newJob(MyJob1.class).withIdentity("job1", "group1").build();

		// Trigger
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();

		// SchedulerFactory
		SchedulerFactory  factory = new StdSchedulerFactory();

		// Scheduler
		Scheduler scheduler = factory.getScheduler();

		scheduler.scheduleJob(jobDetail, trigger);


		// 创建并注册一个全局的Trigger Listener
		scheduler.getListenerManager().addTriggerListener(new MyTriggerListener("myListener1"), EverythingMatcher.allTriggers());

		// 创建并注册一个局部的Trigger Listener
		scheduler.getListenerManager().addTriggerListener(new MyTriggerListener("myListener2"), KeyMatcher.keyEquals(TriggerKey.triggerKey("trigger1", "gourp1")));

		// 创建并注册一个特定组的Trigger Listener
		GroupMatcher<TriggerKey> matcher = GroupMatcher.triggerGroupEquals("gourp1");
		scheduler.getListenerManager().addTriggerListener(new MyTriggerListener("myListener3"), matcher);

		scheduler.start();
		
	}

}
