package com.ithawk.demo.thread.six;

import java.util.concurrent.*;

/**
 * ThreadPoolExecutor 的使用
 *
 * new SynchronousQueue<Runnable>() ：同步拒绝队列-》超过核心线程数 4，就不会加入队列中
 * new LinkedBlockingQueue<Runnable>() ：同步拒绝队列-》超过核心线程数 4，就会加入队列中
 */
public class ThreadPoolExecutorDemo {

    static ExecutorService executorService = new ThreadPoolExecutor(2, 4,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new RejectedExecutionHandler() {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println(".......");
        }
    });


    static ExecutorService executorService1 = new ThreadPoolExecutor(2, 4,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new RejectedExecutionHandler() {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println(".......");
        }
    });


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程池执行线程" + finalI);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executorService1.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程池二执行线程" + finalI);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService1.shutdown();
    }
}
