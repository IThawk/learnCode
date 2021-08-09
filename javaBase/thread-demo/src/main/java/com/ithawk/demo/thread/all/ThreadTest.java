package com.ithawk.demo.thread.all;


public class ThreadTest {

    public static void main(String[]args){
        NotThreadSafe sharedInstance = new NotThreadSafe();
        new Thread(new MyRunnable(sharedInstance)).start();
        new Thread(new MyRunnable(sharedInstance)).start();

    }
}

class MyRunnable implements Runnable{
    NotThreadSafe instance = null;
    public MyRunnable(NotThreadSafe instance){
        this.instance = instance;
    }
    public void run(){
        this.instance.add(" "+Thread.currentThread().getName());
        System.out.println(this.instance.builder.toString());
    }
}

class NotThreadSafe{
    StringBuilder builder = new StringBuilder();

    public void add(String text){
        this.builder.append(text);
    }
}