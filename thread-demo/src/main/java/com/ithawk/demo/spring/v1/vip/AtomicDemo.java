package com.ithawk.demo.spring.v1.vip;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class AtomicDemo {


    private static int count=0;//正确性

    public /*synchronized*/ static void incr(){
        synchronized (AtomicDemo.class) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<1000;i++){
            new Thread(()->AtomicDemo.incr()).start();
        }
        Thread.sleep(5000);
        System.out.println("运行结果："+count);

    }

}
