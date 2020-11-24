package com.ithawk.demo.thread.six;

import java.util.Date;
import java.util.concurrent.*;

/**
 * ThreadPoolExecutor 的使用
 * <p>
 * ThreadPoolExecutor：
 * int corePoolSize, 核心线程数 线程池初始化的线程大小
 * int maximumPoolSize, 最大线程数
 * new SynchronousQueue<Runnable>() ：同步拒绝队列-》超过核心线程数 4，就不会加入队列中，直接进入
 * new LinkedBlockingQueue<Runnable>() ：同步拒绝队列-》超过核心线程数 4，就会加入队列中
 * 设置最大核心数：
 * 1.CPU密集型 尽量使用较小的线程池,一般Cpu核心数+1 因为CPU密集型任务CPU的使用率很高,若开过多的线程,只能增加线程上下文的切换次数,带来额外的开销
 * 2.IO密集型 方法一:可以使用较大的线程池,一般CPU核心数 * 2 IO密集型CPU使用率不高,可以让CPU等待IO的时候处理别...
 * 3.混合型 可以将任务分为CPU密集型和IO密集型,然后分别使用不同的线程池去处理,按情况而定
 */
public class ThreadPoolExecutorDemo {
    int maxCore = Runtime.getRuntime().availableProcessors();
    static ExecutorService executorService = new ThreadPoolExecutor(2, 4,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new RejectedExecutionHandler() {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("线程池一已经满了");
        }
    });


    static ExecutorService executorService1 = new ThreadPoolExecutor(2, 4,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new RejectedExecutionHandler() {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("线程池二已经满了");
        }
    });


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程池一执行线程" + finalI + new Date());

                    try {
                        Thread.sleep(50000);
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
                    System.out.println("线程池二执行线程" + finalI + new Date());
                    s.append(finalI);
                    countDownLatch.countDown();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        countDownLatch.await();
        executorService1.shutdown();
        System.out.println("ddddd:" + s.toString());
    }
}
