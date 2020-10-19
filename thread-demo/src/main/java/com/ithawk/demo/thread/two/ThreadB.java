package com.ithawk.demo.thread.two;

/**
 *
 */
public class ThreadB extends Thread {
    private Object lock;


    public ThreadB(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            System.out.println("start ThreadB");
            //唤醒被阻塞的线程
            lock.notify();
            System.out.println("end ThreadB");
        }
    }
}
