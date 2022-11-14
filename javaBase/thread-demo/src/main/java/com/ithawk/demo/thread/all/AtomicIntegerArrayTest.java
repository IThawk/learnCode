package com.ithawk.demo.thread.all;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayTest {

    public static void main(String[] args) {
        //创建给定长度的AtomicIntegerArray。
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);
        //将位置 i 的元素设置为给定值,默认值为0
        atomicIntegerArray.set(9,10);
        System.out.println("Value: " + atomicIntegerArray.get(9) + "默认值：" + atomicIntegerArray.get(0));
        //返回该数组的长度
        System.out.println("数组长度：" + atomicIntegerArray.length());
        //以原子方式先对给定下标加上特定的值，再获取相加后的值
        atomicIntegerArray.set(0,10);
        System.out.println("Value: " + atomicIntegerArray.get(0));
        System.out.println("Value: " +  atomicIntegerArray.addAndGet(5,10));
        //如果当前值 == 预期值，将位置 i 的元素设置为给定的更新值。
        Boolean bool = atomicIntegerArray.compareAndSet(5,10,30);
        System.out.println("结果值： " + atomicIntegerArray.get(5) + " Result: " + bool);
        //以原子方式先将当前下标的值减1，再获取减1后的结果
        System.out.println("下标为5的值为：" +  atomicIntegerArray.decrementAndGet(5));
        System.out.println("下标为5的值为：" + atomicIntegerArray.get(5));
        //以原子方式先获取当前下标的值，再将当前下标的值加上给定的值
        Integer result2 = atomicIntegerArray.getAndAdd(5,5);
        System.out.println("下标为5的值为：" + result2);
        System.out.println("下标为5的值为：" + atomicIntegerArray.get(5));
        //以原子方式先获取当前下标的值，再对当前下标的值减1
        System.out.println("下标为1的值为：" + atomicIntegerArray.getAndDecrement(1));
        System.out.println("下标为1的值为：" + atomicIntegerArray.get(1));
        // 以原子方式先获取当前下标的值，再对当前下标的值加1
        System.out.println("下标为2的值为：" + atomicIntegerArray.getAndIncrement(2));
        System.out.println("下标为2的值为：" + atomicIntegerArray.get(2));
        //将位置 i 的元素以原子方式设置为给定值，并返回旧值。
        System.out.println("下标为3的值为：" + atomicIntegerArray.getAndSet(3,50));
        System.out.println("下标为3的值为：" + atomicIntegerArray.get(3));
        //以原子方式先对下标加1再获取值
        System.out.println("下标为4的值为：" + atomicIntegerArray.incrementAndGet(4));
        System.out.println("下标为4的值为：" + atomicIntegerArray.get(4));

    }

}
