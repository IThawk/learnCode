package com.ithawk.demo.thread.five;

import java.util.concurrent.CountDownLatch;

/**
 *  CountDownLatch 就会设置一个数值，当这个数值为零的时候就会去除countDownLatch.await()阻塞
 */
public class CountDownLatchDemo extends Thread {

    static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new CountDownLatchDemo().start();

        }
        System.out.println("--------------------------");
        Thread.sleep(3000);
        for (int i = 0; i < 5; i++) {
            System.out.println("kais");
            new CountDownLatchDemo().start();
//            countDownLatch.countDown();
        }

    }

    @Override
    public void run() {
        try {
//            System.out.println("等待");
            Thread.sleep(2000);
            countDownLatch.countDown();
            countDownLatch.await(); //阻塞  3个线程 Thread.currentThread

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //TODO
        System.out.println("ThreadName阻塞完成:" + Thread.currentThread().getName());
    }




  /*  public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch =new CountDownLatch(3);
        new Thread(()->{
            System.out.println("Thread1");
            countDownLatch.countDown(); //3-1=2
        }).start();
        new Thread(()->{
            System.out.println("Thread2");
            countDownLatch.countDown();//2-1=1
        }).start();
        new Thread(()->{
            System.out.println("Thread3");
            countDownLatch.countDown();//1-1=0
        }).start();
        countDownLatch.await();
    }*/
}
