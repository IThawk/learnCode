package com.ithawk.demo.thread.one;

import java.util.concurrent.TimeUnit;

/**
 * 线程状态
 */
public class ThreadStatusDemo {

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Time_Waiting_Thread").start();

        new Thread(() -> {
            while (true) {
                synchronized (ThreadStatusDemo.class) {
                    try {
                        ThreadStatusDemo.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "Wating_Thread").start();

        //BLOCKED
        new Thread(new BlockedDemo(), "Blocke01_Thread").start();
        new Thread(new BlockedDemo(), "Blocke02_Thread").start();
    }

    static class BlockedDemo extends Thread {

        @Override
        public void run() {
            synchronized (BlockedDemo.class) {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
