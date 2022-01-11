package com.ithawk.demo.quartz.standalone.jdktimer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * @Author:
 * @Date:
 * @Description:
 */
public class TestTimerTask extends TimerTask {
    /**
     * 此计时器任务要执行的操作。
     */
    public void run() {
        Date executeTime = new Date(this.scheduledExecutionTime());
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("任务执行了：" + dateStr);
    }
}
