package com.ithawk.demo.thread;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ithawk
 * @projectName javaBase
 * @description: TODO
 * @date 2022/1/2015:17
 */
public class Demo {


    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor threadPoolExecutor1 = new ThreadPoolExecutor(4,2000,
                1, TimeUnit.MICROSECONDS,new LinkedBlockingQueue<>(10));
        ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(40,200,
                1, TimeUnit.MICROSECONDS,new LinkedBlockingQueue<>(2000));
        ThreadPoolExecutor threadPoolExecutor3 = new ThreadPoolExecutor(40,200,
                1, TimeUnit.MICROSECONDS,new LinkedBlockingQueue<>(2000));
        ThreadPoolExecutor threadPoolExecutor4 = new ThreadPoolExecutor(40,200,
                1, TimeUnit.MICROSECONDS,new LinkedBlockingQueue<>(2000));
        ThreadPoolExecutor threadPoolExecutor5 = new ThreadPoolExecutor(40,200,
                1, TimeUnit.MICROSECONDS,new LinkedBlockingQueue<>(2000));
        ThreadPoolExecutor threadPoolExecutor6 = new ThreadPoolExecutor(40,200,
                1, TimeUnit.MICROSECONDS,new LinkedBlockingQueue<>(2000));


        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i=0;i<2000;i++){
            threadPoolExecutor1.execute(()->{
                System.out.println("11111111111111111111111111");
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    countDownLatch.await(20,TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1111111111end1111111111111");
            });
//            threadPoolExecutor2.execute(()->{
//                System.out.println("22222222222222222222222222");
//                try {
//                    Thread.sleep(6000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("22222222222end222222222222");
//            });
//            threadPoolExecutor3.execute(()->{
//                System.out.println("33333333333333333333333333");
//                try {
//                    Thread.sleep(6000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("333333333333end33333333333");
//            });
//            threadPoolExecutor4.execute(()->{
//                System.out.println("44444444444444444444444444");
//                try {
//                    Thread.sleep(6000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("44444444444end444444444444");
//            });
//            threadPoolExecutor5.execute(()->{
//                System.out.println("55555555555555555555555555");
//                try {
//                    Thread.sleep(6000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("555555555555end55555555555");
//            });
//            threadPoolExecutor6.execute(()->{
//                System.out.println("6666666666666666666666666");
//                try {
//                    Thread.sleep(6000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("666666666666end6666666666");
//            });
        }

        try {
            Thread.sleep(60000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
        System.in.read();
    }
}
