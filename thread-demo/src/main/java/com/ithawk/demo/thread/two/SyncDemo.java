package com.ithawk.demo.thread.two;

/**
 *
 */
public class SyncDemo {
    Object lock;

    public SyncDemo(){

    }
  /*  public SyncDemo(Object lock){
        this.lock=lock;
    }*/
    public void demo(){
        synchronized (this){ //

        }
    }

    public static void main(String[] args) {

        SyncDemo sd=new SyncDemo();
//        SyncDemo sd2=new SyncDemo();

        new Thread(()->sd.demo()).start();
        new Thread(()->sd.demo()).start();
    }
}
