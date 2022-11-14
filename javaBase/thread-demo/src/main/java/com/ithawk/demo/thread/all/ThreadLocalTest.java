package com.ithawk.demo.thread.all;
public class ThreadLocalTest {

    public static class MyRunnable implements Runnable {

        private ThreadLocal threadLocal = new ThreadLocal();

        public void run() {
            threadLocal.set((int) (Math.random() * 100D));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
            System.out.println(threadLocal.get());
        }
    }

    public static void main(String[] args) {
        MyRunnable sharedInstance = new MyRunnable();
        Thread thread1 = new Thread(sharedInstance);
        Thread thread2 = new Thread(sharedInstance);
        thread1.start();
        thread2.start();
    }

}
