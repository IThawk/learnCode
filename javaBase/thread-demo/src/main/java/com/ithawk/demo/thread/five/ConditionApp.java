package com.ithawk.demo.thread.five;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Hello world!
 */
public class ConditionApp {
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock(); //重入锁
        Condition condition = lock.newCondition();
        lock.newCondition();
        lock.newCondition();
        lock.newCondition();
        lock.newCondition();
        //step 2
        new Thread(new ConditionWait(lock, condition)).start();// 阻塞await
        //step 1
        //(condtion为空)
        new Thread(new ConditionNotify(lock, condition)).start();//


        Thread.sleep(10000);
        lock.lock();
        condition.signalAll();
        System.out.println("唤醒所有的condition");
        lock.unlock();
    }
}
