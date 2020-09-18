package com.gupaoedu.vip;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class WaitNotifyDemo {

    public static void main(String[] args) {
        Object lock=new Object();
        ThreadA threadA=new ThreadA(lock);
        threadA.start();
        ThreadB threadB=new ThreadB(lock);
        threadB.start();
    }
}
