package com.ithawk.demo.thread.five;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 *
 */
public class ConditionNotify implements Runnable{

    private Lock lock;
    private Condition condition;

    public ConditionNotify(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        try{
            lock.lock();//获得了锁.
            System.out.println("begin - conditionNotify");
            condition.signal();//唤醒阻塞状态的线程

            //if(uncondition){
//                condition.await();
            // }
            //condition.notify;

            condition.await();
            System.out.println("end - conditionNotify");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); //释放锁
            System.out.println("ConditionNotify wait for conditionNotify");

        }
    }
}
