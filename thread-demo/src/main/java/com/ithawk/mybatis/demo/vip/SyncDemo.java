package com.ithawk.mybatis.demo.vip;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
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
