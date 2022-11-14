package com.ithawk.demo.spring5.webflux.ps;

import java.util.Random;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class SomeTest {
    public static void main(String[] args) {
        SubmissionPublisher<Integer> publisher = null;
        try {
            // 创建一个发布者
            publisher = new SubmissionPublisher();
            // 创建一个订阅者
            SomeSubscriber subscriber = new SomeSubscriber();
            // 确立订阅关系，该方法的执行与其所触发的onSubscribe()方法是异步执行的
            publisher.subscribe(subscriber);

            // 生产消息
            for(int i = 0; i < 300; ++i) {
                // 生成一个[0，100)随机数
                int item = new Random().nextInt(100);
                System.out.println("生产出第" + i + "条消息" + item);
                // 发布消息。当publisher的buffer满时该方法会阻塞
                publisher.submit(item);
            }
        } finally {
            // 关闭生产者
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

