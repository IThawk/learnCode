package com.ithawk.demo.thread.one;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class ExceptionThreadDemo {
    private static int i;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {//默认是false  _interrupted state?

                try {
                    //中断一个处于阻塞状态的线程。join/wait/queue.take..
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println("demo");
                } catch (InterruptedException e) {
                    System.out.println("中断存于等待状态的线程，出现错误");
                    e.printStackTrace();
                    break;
                }
            }
            System.out.println("i:" + i);
        });
        thread.start();

        TimeUnit.SECONDS.sleep(1);
        thread.interrupt(); //把isInterrupted设置成true

        System.out.println(thread.isInterrupted()); //true
    }
}
