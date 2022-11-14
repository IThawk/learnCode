package com.ithawk.demo.thread.all;

import java.util.concurrent.*;

public class ScheduledThreadPoolExecutorTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
       ScheduledThreadPoolExecutorTest.scheduleWithFixedDelay();
  //      ScheduledThreadPoolExecutorTest.scheduleAtFixedRate();
//        ScheduledThreadPoolExecutorTest.scheduleCaller();
//        ScheduledThreadPoolExecutorTest.scheduleRunable();
    }

    // 任务以固定时间间隔执行，延迟5s后开始执行任务，任务执行完毕后间隔5s再次执行，依次往复
    static void scheduleWithFixedDelay() throws InterruptedException, ExecutionException {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(10);

        ScheduledFuture<?> result = executorService.scheduleWithFixedDelay(new Runnable() {
            public void run() {

                System.out.println(System.currentTimeMillis());
            }
        }, 5000, 5000, TimeUnit.MILLISECONDS);

        // 由于是定时任务，一直不会返回
        result.get();
        System.out.println("over");

    }
    // 相对开始加入任务的时间点固定频率执行：从加入任务开始算2s后开始执行任务，2+5s开始执行，2+2*5s执行，2+n*5s开始执行；
    // 但是如果执行任务时间大于5s,则不会并发执行，后续任务将会延迟。

    static void scheduleAtFixedRate() throws InterruptedException, ExecutionException {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(10);

        ScheduledFuture<?> result = executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis());

            }
        }, 2000, 5000, TimeUnit.MILLISECONDS);

        // 由于是定时任务，一直不会返回
        result.get();
        System.out.println("over");
    }

    // 延迟2s后开始执行，只执行一次，没有返回值
    static void scheduleRunable() throws InterruptedException, ExecutionException {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(10);

        ScheduledFuture<?> result = executorService.schedule(new Runnable() {

            @Override
            public void run() {
                System.out.println("gh");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, 2000, TimeUnit.MILLISECONDS);

        System.out.println(result.get());

    }

    // 延迟2s后开始执行，只执行一次，有返回值
    static void scheduleCaller() throws InterruptedException, ExecutionException {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(10);

        ScheduledFuture<String> result = executorService.schedule(new Callable<String>() {

            @Override
            public String call() throws Exception {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return "gh";
            }

        }, 2000, TimeUnit.MILLISECONDS);

        // 阻塞，直到任务执行完成
        System.out.print(result.get());

    }
}
