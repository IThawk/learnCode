package com.ithawk.demo.spring5.webflux.pps;

import java.util.Random;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class SomeTest {

    public static void main(String[] args) {
        SubmissionPublisher<Integer> publisher = null;
        try {
            publisher = new SubmissionPublisher();
            // 创建订阅者
            SomeSubscriber subscriber = new SomeSubscriber();
            // 创建处理器
            SomeProcessor processor = new SomeProcessor();
            // 建立订阅关系
            publisher.subscribe(processor);
            processor.subscribe(subscriber);
            for(int i = 0; i < 300; ++i) {
                int item = new Random().nextInt(100);
                System.out.println("开始生产消息" + i);
                publisher.submit(item);
            }
        } finally {
            publisher.close();
        }
        try {
            System.out.println("主线程开始等待");
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

