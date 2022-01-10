package com.xxl.job.executor.service.jobhandler;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
    public static void main(String[] args) {
        //创建计时器
        Timer timer = new Timer("MyTask");
        //创建计时器任务(TimerTaskBean:是自定义的类，继承了TimerTask抽象类)
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("假装在执行任务");
            }
        };
        // 5秒钟以后执行
        timer.schedule(task, 5000);

    }
}
