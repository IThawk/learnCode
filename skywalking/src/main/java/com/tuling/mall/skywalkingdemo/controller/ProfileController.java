package com.tuling.mall.skywalkingdemo.controller;


import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Fox
 */
@RestController
public class ProfileController {

    @RequestMapping("/profile")
    public String profile() {
        process();
        return "success";
    }

    @Trace
    private void process() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                countDownLatch.countDown();
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                // countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
