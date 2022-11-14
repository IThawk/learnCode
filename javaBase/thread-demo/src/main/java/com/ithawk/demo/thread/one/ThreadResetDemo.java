package com.ithawk.demo.thread.one;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class ThreadResetDemo {

    //1. Thread.interrupted()
    //2. InterruptedException

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {//默认是false  _interrupted state?
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("before:" + Thread.currentThread().isInterrupted());
                    Thread.interrupted(); //复位- 回到初始状态
                    System.out.println("after:" + Thread.currentThread().isInterrupted());
                }
            }
        });
        thread.start();

        TimeUnit.SECONDS.sleep(1);
        thread.interrupt(); //把isInterrupted设置成true
    }
}
