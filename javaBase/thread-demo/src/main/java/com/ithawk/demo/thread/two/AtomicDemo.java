package com.ithawk.demo.thread.two;

/**
 *
 */
public class AtomicDemo {


    //使用多线程的时候出现数据不符合预期情况
    private static int count = 0;//正确性

    private static final Object lock = new Object();//正确性

    /**
     * 注释的地方和下面的作用域范围是相同的，这样的使用方法都是锁住了整个对象
     */
    public /*synchronized*/ static void incr() {
        synchronized (AtomicDemo.class) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
    }

    public static void incr2() {
        //注意这个锁的对象不能是Null
        synchronized (lock) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
//            new Thread(() -> AtomicDemo.incr()).start();
            new Thread(() -> AtomicDemo.incr2()).start();
        }
        Thread.sleep(5000);
        System.out.println("运行结果：" + count);

    }

}
