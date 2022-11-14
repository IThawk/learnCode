package com.ithawk.demo.quartz.standalone.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * @Author:
 * @Date:
 * @Description:
 */
public class MyJobListener implements JobListener {

    public String getName() {
        String name = getClass().getSimpleName();
        System.out.println( "Method 111111 :"+ "获取到监听器名称："+name);
        return name;
    }

    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        System.out.println("Method 222222 :"+ jobName + " ——任务即将执行 ");
    }

    public void jobExecutionVetoed(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        System.out.println("Method 333333 :"+ jobName + " ——任务被否决 ");
    }

    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobName = context.getJobDetail().getKey().getName();
        System.out.println("Method 444444 :"+ jobName + " ——执行完毕 ");
        System.out.println("------------------");
    }
}
