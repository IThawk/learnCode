package com.ithawk.demo.thread.three;

/**
 * Hello world!
 */
public class App {

    public volatile static boolean stop = false;

    public static boolean stop1 = false;

    public static void main(String[] args) throws InterruptedException {

        VolatileDemo volatileDemo = new VolatileDemo();
        Thread t1 = new Thread(() -> {
            System.out.println("stop " + stop);
            System.out.println("stop1 " + stop1);
            while (!volatileDemo.flag) {

            }
            if (volatileDemo.a){
                System.out.println("...................");
            }
            System.out.println("stop " + stop);
            System.out.println("stop1 " + stop1);
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stop1 = true;
            stop = true;
//            volatileDemo.writer();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            volatileDemo.writer();

        });


        t1.start();
        t2.start();
        Thread.sleep(1000);
//        stop = true; //true
    }
}
