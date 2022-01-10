package com.ithawk.demo.quartz.standalone.jdktimer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author: qingshan
 * @Date: 2019/9/3 22:32
 * @Description: 咕泡学院，只为更好的你
 */
public class TestTimer {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TestTimerTask();
        timer.schedule(task, 5000L, 1000L);
    }
}
