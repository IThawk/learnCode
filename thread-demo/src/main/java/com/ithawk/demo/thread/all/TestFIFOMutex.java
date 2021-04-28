package com.ithawk.demo.thread.all;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

class FIFOMutex  {
    private final AtomicBoolean locked = new AtomicBoolean(false);
    private final Queue<Thread> waiters = new ConcurrentLinkedQueue<>();

    public void lock(){
        Thread current = Thread.currentThread();
        waiters.add(current);
        // 如果当前线程不在队首，或锁已被占用，则当前线程阻塞
        // 这个判断的内在意图：锁必须由队首元素拿到
        while (waiters.peek() != current || !locked.compareAndSet(false,true)){
            LockSupport.park();
        }
        waiters.remove();// 删除队首元素
    }


    public void unlock(){
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }
}
public class TestFIFOMutex {
    public static void main(String[] args) throws InterruptedException {
        FIFOMutex mutex = new FIFOMutex();
        MyThread a1 = new MyThread("a", mutex);
        MyThread a2 = new MyThread("b", mutex);
        MyThread a3 = new MyThread("c", mutex);
        a1.start();
        a2.start();
        a3.start();
        a1.join();
        a2.join();
        a3.join();
        System.out.println("Finished");
    }
}

class MyThread extends Thread{
    private String name;
    private FIFOMutex mutex;
    private static int count;
    public MyThread(String name, FIFOMutex mutex) {
        this.name = name;
        this.mutex = mutex;

    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            mutex.lock();
            count++;
            System.out.println("thread:"+Thread.currentThread().getName()+" name:" + name + " count:" + count);
            mutex.unlock();
        }
    }
}