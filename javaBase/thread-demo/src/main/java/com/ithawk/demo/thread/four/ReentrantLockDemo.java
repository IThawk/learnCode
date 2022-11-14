package com.ithawk.demo.thread.four;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class ReentrantLockDemo {

    static Lock lock = new ReentrantLock();
    //synchronized的原子操作改造成Lock

    public static void demo1() { //N线程来访问
        lock.lock(); //获得一个锁
        System.out.println("demo1 lock");
        lock.unlock();// 释放锁
        System.out.println("demo1 unlock");
    }

    public static void demo2() { //N线程来访问
        lock.lock(); //获得一个锁
        System.out.println("demo2 lock");
        lock.unlock();// 释放锁
        System.out.println("demo2 unlock");
    }

    public static void main(String[] args) {
        new Thread(() -> {
            demo1();
        }).start();

        new Thread(() -> {
            demo2();
        }).start();

    }
}
