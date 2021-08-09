package com.ithawk.demo.thread.three;

/**
 *
 */
public class SyncDemo {
    private App app=new App();
    public App getApp(){
        return app;
    }


    public void demo() {
        synchronized (this) {//ThreadA / ThreadB
        }
    }
}
