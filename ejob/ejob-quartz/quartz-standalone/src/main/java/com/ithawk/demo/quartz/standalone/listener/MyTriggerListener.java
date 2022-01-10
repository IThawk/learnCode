package com.ithawk.demo.quartz.standalone.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * @Author: qingshan
 * @Date: 2018/9/19 15:19
 * @Description: 咕泡学院，只为更好的你
 */
public class MyTriggerListener implements TriggerListener {
    private String name;

    public MyTriggerListener(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Trigger 被触发，Job 上的 execute() 方法将要被执行时
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        String triggerName = trigger.getKey().getName();
        System.out.println("Method 11111 " + triggerName + " was fired");
    }

    // 在 Trigger 触发后，Job 将要被执行时由 Scheduler 调用这个方法
    // 返回true时，这个任务不会被触发
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        String triggerName = trigger.getKey().getName();
        System.out.println("Method 222222 " + triggerName + " was not vetoed");
        return false;
    }

    public void triggerMisfired(Trigger trigger) {
        String triggerName = trigger.getKey().getName();
        System.out.println("Method 333333 " + triggerName + " misfired");
    }

    public void triggerComplete(Trigger trigger, JobExecutionContext context,
                                Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        String triggerName = trigger.getKey().getName();
        System.out.println("Method 444444 " + triggerName + " is complete");
        System.out.println("------------");
    }
}
