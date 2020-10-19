package com.ithawk.demo.thread.four;

/**
 *
 */
public class App {

    public synchronized void demo(){ //main获得对象锁
        System.out.println("demo");
        demo2();
    }
    public void demo2(){
        synchronized (this) { //增加重入次数就行
            System.out.println("demo2");
        }//减少重入次数
    }

    public static void main(String[] args) {
        App app=new App();
        app.demo();
    }
}
