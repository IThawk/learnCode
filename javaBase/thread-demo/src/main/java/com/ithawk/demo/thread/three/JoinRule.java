package com.ithawk.demo.thread.three;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class JoinRule {


    static int x = 0;

    public static void main(String[] args) throws InterruptedException {
        /*Thread t1=new Thread(()->{
            x=100;
        });
        t1.start();
        t1.join();
        System.out.println(x);*/

        Thread t1 = new Thread(() -> {
            System.out.println("t1");
            //执行的结果对于主线程可见
        });
        Thread t2 = new Thread(() -> {
            System.out.println("t2");
        });
        Thread t3 = new Thread(() -> {
            System.out.println("t3");
        });
        t1.start();
        t1.join(); //阻塞主线程 wait/notify
        //等到阻塞释放
        //获取到t1线程的执行结果.
        t2.start();
        t2.join(); // 建立一个happens-bebefore规则

        t3.start();
    }
}
