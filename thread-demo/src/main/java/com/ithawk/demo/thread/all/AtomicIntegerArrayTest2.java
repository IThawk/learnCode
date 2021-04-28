package com.ithawk.demo.thread.all;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayTest2 {

    static AtomicIntegerArray arr = new AtomicIntegerArray(10);
    public static class AddThread implements Runnable{
        public void run () {
        for (int k = 0; k < 10; k++)
            arr.getAndIncrement(k % arr.length());
    }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] ts = new Thread[10];
        for (int k = 0; k < 10; k++) {
            ts[k] = new Thread(new AddThread());
        }
        for (int k = 0; k < 10; k++) {
            ts[k].start();
        }
        for (int k = 0; k < 10; k++) {
            ts[k].join();
        }
        System.out.println(arr);
    }
}
