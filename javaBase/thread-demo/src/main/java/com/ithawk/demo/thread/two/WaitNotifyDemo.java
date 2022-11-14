package com.ithawk.demo.thread.two;

/**
 * 现在之间进行通信
 * 线程B执行的时候才会唤醒线程A
 */
public class WaitNotifyDemo {

    public static void main(String[] args) {
        Object lock = new Object();
        ThreadA threadA = new ThreadA(lock);
        threadA.start();
        ThreadB threadB = new ThreadB(lock);
        threadB.start();
    }
}
