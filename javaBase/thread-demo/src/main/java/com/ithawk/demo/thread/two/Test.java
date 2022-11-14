package com.ithawk.demo.thread.two;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 *
 */
public class Test implements Runnable{

    private static volatile boolean isRunning=false; //中断标识

    @Override
    public void run() {
        while(!isRunning){ //!true
            //TODO
        }

    }

    public static void main(String[] args) throws InterruptedException {
//        System.out.println("test");
        Map<Integer,String> test2= new HashMap<>();
        test2.put(0,"tes");
        test2.put(null,"2222");
        System.out.println(test2.get(0));

        Map<String,String> test= new HashMap<>();
        Map<String,String> test1= new Hashtable<>();
        test1.containsKey("dd");
        Integer h = 0;
        Character integer = 'c' ;

        System.out.println(    integer.hashCode());
        System.out.println(h.hashCode());
        String j = new String("cc");
        System.out.println(j.hashCode());
        System.out.println( (h = "key".hashCode()) ^ (h >>> 16));
        test.put(null,"s");
        Thread thread=new Thread(()->{
            System.out.println("test1");
            test("test1",test);
            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread1=new Thread(()->{
            System.out.println("test2");
            test("test2",test);
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread.start();

        Thread.sleep(10000);

//        thread.interrupt();//isRunning=true;
    }

    public static void test(String dd ,Map<String,String> test) {
        System.out.println(test.get("test"));
        test.put("test",dd);

        System.out.println(test.get("test"));
    }

}
