package com.ithawk.demo.thread.five;

import java.util.concurrent.Semaphore;

/**
 *
 */
public class SemaphoreDemo {

    //限流（AQS）

    //permits; 令牌(5)

    //公平和非公平


    static class Car extends Thread {
        private int num;
        private Semaphore semaphore;

        public Car(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        public void run() {
            try {
                semaphore.acquire(); //获得一个令牌, 如果拿不到令牌，就会阻塞
                System.out.println("第" + num + " 抢占一个车位");
                Thread.sleep(2000);
                System.out.println("第" + num + " 开走喽");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 10; i++) {
            new Car(i, semaphore).start();
        }
    }


}
