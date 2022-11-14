package com.ithawk.demo.thread.seven;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Hello world!
 *
 */
public class App 
{
    static int count;

    static AtomicInteger atomicInteger=new AtomicInteger(0);

    //序号. -> 序号重叠.

    static void incr(){
        atomicInteger.incrementAndGet();
//        count++;
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        for(int i=0;i<1000;i++){
            new Thread(App::incr).start();
        }


    }
}
