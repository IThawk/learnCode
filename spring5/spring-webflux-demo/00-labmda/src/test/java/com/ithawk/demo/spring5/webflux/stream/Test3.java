package com.ithawk.demo.spring5.webflux.stream;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Test3 {

    // 静态方法：打印一个元素（黑色）休眠一次
    public static void print(int i) {
        System.out.println(i);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 静态方法：打印一个元素（红色）休眠一次
    public static void printRed(int i) {
        System.err.println(i);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 静态方法：打印一个元素及处理它的线程休眠一次
    public static void printThread(int i) {
        String name = Thread.currentThread().getName();
        System.out.println(i + " -- " + name);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 串行处理
    @Test
    public void test01() {
        IntStream.range(1, 100)
                // .sequential()   // 默认
                .peek(Test3::print)
                .count();
    }

    // 并行处理
    @Test
    public void test02() {
        IntStream.range(1, 100)
                .parallel()
                .peek(Test3::print)
                .count();
    }

    // 串并行混合处理：先并行后串行
    // 最终执行效果为后者：串行处理
    @Test
    public void test03() {
        IntStream.range(1, 100)
                .parallel()            // 并行处理
                .peek(Test3::print)
                .sequential()          // 串行处理
                .peek(Test3::printRed)
                .count();
    }

    // 串并行混合处理：先串行后并行
    // 最终执行效果为后者：并行处理
    @Test
    public void test04() {
        IntStream.range(1, 100)
                .sequential()          // 串行处理
                .peek(Test3::printRed)
                .parallel()            // 并行处理
                .peek(Test3::print)
                .count();
    }

    // 串行处理仅有一个main线程
    @Test
    public void test05() {
        IntStream.range(1, 100)
                // .sequential()   // 默认
                .peek(Test3::printThread)
                .count();
    }

    // 并行处理的默认线程数量
    @Test
    public void test06() {
        IntStream.range(1, 100)
                .parallel()
                .peek(Test3::printThread)
                .count();
    }

    // 修改默认线程池中的线程数量
    @Test
    public void test07() {
        // 指定默认线程池中的数量为16，其中包含指定的15个与main线程
        String key = "java.util.concurrent.ForkJoinPool.common.parallelism";
        System.setProperty(key, "15");

        IntStream.range(1, 100)
                .parallel()            // 并行处理
                .peek(Test3::printThread)
                .count();
    }

    // 使用自定义线程池
    @Test
    public void test08() {
        // 创建线程池，包含4个线程
        ForkJoinPool pool = new ForkJoinPool(4);
        // 定义并行任务
        Callable<Long> task = () -> IntStream.range(1, 100)
                                        .parallel()     // 并行处理
                                        .peek(Test3::printThread)
                                        .count();
        // 向线程池提交并行任务
        pool.submit(task);

        // 将pool阻塞，即会阻塞main线程的执行
        synchronized (pool) {
            try {
                pool.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
