package com.ithawk.demo.thread.five;

import java.util.concurrent.CyclicBarrier;

/**
 *
 */
public class CycliBarrierDemo extends Thread {
    @Override
    public void run() {
        System.out.println("开始进行数据分析");
    }

    //循环屏障
    //可以使得一组线程达到一个同步点之前阻塞.

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new CycliBarrierDemo());
        new Thread(new DataImportThread(cyclicBarrier, "file1")).start();
        new Thread(new DataImportThread(cyclicBarrier, "file2")).start();
        new Thread(new DataImportThread(cyclicBarrier, "file3")).start();

    }

}
