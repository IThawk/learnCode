package com.ithawk.demo.thread.three;

/**
 *
 */
public class VolatileDemo {

    boolean a = false;

    volatile boolean flag = false;

    public void writer() { //线程A
        a = true;             //1
        flag = true;       //2

    }

}
