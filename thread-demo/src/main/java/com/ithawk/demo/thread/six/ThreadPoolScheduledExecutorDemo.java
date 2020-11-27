package com.ithawk.demo.thread.six;

import java.util.Date;
import java.util.concurrent.*;

/**
 * ThreadPoolExecutor 的使用
 * <p>
 * ScheduledThreadPoolExecutor：
 *     public ScheduledThreadPoolExecutor(int corePoolSize,
 *                                        ThreadFactory threadFactory,
 *                                        RejectedExecutionHandler handler) {
 *         super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
 *               new DelayedWorkQueue(), threadFactory, handler);
 * 设置最大核心数：
 * 1.CPU密集型 尽量使用较小的线程池,一般Cpu核心数+1 因为CPU密集型任务CPU的使用率很高,若开过多的线程,只能增加线程上下文的切换次数,带来额外的开销
 * 2.IO密集型 方法一:可以使用较大的线程池,一般CPU核心数 * 2 IO密集型CPU使用率不高,可以让CPU等待IO的时候处理别...
 * 3.混合型 可以将任务分为CPU密集型和IO密集型,然后分别使用不同的线程池去处理,按情况而定
 */
public class ThreadPoolScheduledExecutorDemo {
    int maxCore = Runtime.getRuntime().availableProcessors();
    static ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(5,
            Executors.defaultThreadFactory(), new RejectedExecutionHandler() {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("线程池一已经满了");
        }
    });



    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        StringBuffer s = new StringBuffer();
        System.out.println("ddddd:" + new Date());

        for (int i =0 ;i<10;i++){
            executorService.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程池YI执行线程" + "111" + new Date());
                    s.append("111");
                    countDownLatch.countDown();
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                }
            },50,TimeUnit.SECONDS);
        }
        executorService.shutdown();

    }
}
