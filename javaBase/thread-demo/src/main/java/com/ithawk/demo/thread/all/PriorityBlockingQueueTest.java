package com.ithawk.demo.thread.all;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<PriorityElement> queue = new PriorityBlockingQueue<>();
        for (int i = 0; i < 5; i++) {
            Random random=new Random();
            PriorityElement ele = new PriorityElement(random.nextInt(10));
            queue.put(ele);
        }
        while(!queue.isEmpty()){
            System.out.println(queue.take());
        }
    }
}
 class PriorityElement implements Comparable<PriorityElement> {
    private int priority;//定义优先级
    PriorityElement(int priority) {
        //初始化优先级
        this.priority = priority;
    }
    @Override
    public int compareTo(PriorityElement o) {
        //按照优先级大小进行排序
        return priority >= o.getPriority() ? 1 : -1;
    }
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    @Override
    public String toString() {
        return "PriorityElement [priority=" + priority + "]";
    }
}