package com.ithawk.demo.quartz.springboot.demo.task;

import com.ithawk.demo.quartz.springboot.demo.util.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: qingshan
 * @Date: 2018/9/11 16:31
 * @Description: 咕泡学院，只为更好的你
 */
@DisallowConcurrentExecution
public class TestTask1 implements BaseJob {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(Thread.currentThread().getName() + " " +sdf.format(date) + " Task1： ----咕泡学院，只为更好的你----");
/*        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
