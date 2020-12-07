package com.ithawk.demo.thread.two;

import java.io.IOException;


public class TreadLocalDemo {
    /**
     * ThreadLocal 各自的线程的变量
     * 当线程第一次调用ThreadLocal的set或者get方法的时候才会创建他们（后面我们会查看这两个方法的源码）。
     * 除此之外，和我所想的不同的是，每个线程的本地变量不是存放在ThreadLocal实例中，而是放在调用线程的
     * ThreadLocals变量里面（前面也说过，该变量是Thread类的变量）。也就是说，ThreadLocal类型的本地
     * 变量是存放在具体的线程空间上，其本身相当于一个装载本地变量的工具壳，通过set方法将value添加到调用线
     * 程的threadLocals中，当调用线程调用get方法时候能够从它的threadLocals中取出变量。如果调用线程一
     * 直不终止，那么这个本地变量将会一直存放在他的threadLocals中，所以不使用本地变量的时候需要调用remove
     * 方法将threadLocals中删除不用的本地变量。
     * <p>
     *     六、从ThreadLocalMap看ThreadLocal使用不当的内存泄漏问题
     * 1、基础概念
     * 　　首先我们先看看ThreadLocalMap的类图，在前面的介绍中，我们知道ThreadLocal只是一个工具类，
     * 他为用户提供get、set、remove接口操作实际存放本地变量的threadLocals（调用线程的成员变量），
     * 也知道threadLocals是一个ThreadLocalMap类型的变量，下面我们来看看ThreadLocalMap这个类。
     * 在此之前，我们回忆一下Java中的四种引用类型，相关GC只是参考前面系列的文章(JVM相关)
     *
     * ①强引用：Java中默认的引用类型，一个对象如果具有强引用那么只要这种引用还存在就不会被GC。
     *
     * ②软引用：简言之，如果一个对象具有弱引用，在JVM发生OOM之前（即内存充足够使用），是不会GC这个对象的；
     * 只有到JVM内存不足的时候才会GC掉这个对象。软引用和一个引用队列联合使用，如果软引用所引用的对象被回收之后，
     * 该引用就会加入到与之关联的引用队列中
     *
     * ③弱引用（这里讨论ThreadLocalMap中的Entry类的重点）：如果一个对象只具有弱引用，
     * 那么这个对象就会被垃圾回收器GC掉(被弱引用所引用的对象只能生存到下一次GC之前，当发生GC时候，
     * 无论当前内存是否足够，弱引用所引用的对象都会被回收掉)。弱引用也是和一个引用队列联合使用，
     * 如果弱引用的对象被垃圾回收期回收掉，JVM会将这个引用加入到与之关联的引用队列中。若引用的对象可以通过弱引用的get方法得到，
     * 当引用的对象呗回收掉之后，再调用get方法就会返回null
     *
     * ④虚引用：虚引用是所有引用中最弱的一种引用，其存在就是为了将关联虚引用的对象在被GC掉之后收到一个通知。
     * （不能通过get方法获得其指向的对象）
     * </p>
     * <p>
     *     　　总结：THreadLocalMap中的Entry的key使用的是ThreadLocal对象的弱引用，在没有其他地方对ThreadLoca依赖，
     *     ThreadLocalMap中的ThreadLocal对象就会被回收掉，但是对应的不会被回收，这个时候Map中就可能存在key为null但是
     *     value不为null的项，这需要实际的时候使用完毕及时调用remove方法避免内存泄漏。
     * </p>
     */
    private static ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                stringThreadLocal.set("thread1");
                System.out.println(stringThreadLocal.get());;
            }
        };
        thread.start();
        Thread.sleep(10000);
        Thread thread1 = new Thread()  {
            @Override
            public void run() {

                System.out.println(stringThreadLocal.get());;
            }
        };
        thread1.start();
        System.in.read();
    }
}
