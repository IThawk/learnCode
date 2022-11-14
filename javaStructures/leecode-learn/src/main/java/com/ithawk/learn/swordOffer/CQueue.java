package com.ithawk.learn.swordOffer;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/yong-liang-ge-zhan-shi-xian-dui-lie-lcof
 */
public class CQueue {

    //利用的是 栈的先入后出
    public Stack<Integer> stack1;
    public Stack<Integer> stack2;
    //利用的是 队列的的先入先出
    public Queue<Integer> queue;

    public CQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
        queue= new ArrayDeque<>();
    }

    public void appendTail(int value) {
        while (!stack2.empty()){
            stack1.push(stack2.pop());
        }
        this.stack1.push(value);
        this.queue.add(value);
    }

    public int deleteHead() {
        if (this.queue==null){
            return -1;
        }
        if (this.queue.isEmpty()){
            return -1;
        }
        return this.queue.poll();
//        System.out.println("-----"+queue.poll());
//        while (!stack1.empty()){
//            stack2.push(stack1.pop());
//        }
//        if (this.stack2.empty()) {
//            return -1;
//        } else {
//            return this.stack2.pop();
//        }
    }

    public static void main(String[] args) {
        CQueue cQueue = new CQueue();
        cQueue.appendTail(1);
        cQueue.appendTail(2);
        cQueue.appendTail(3);
        cQueue.appendTail(4);
        System.out.println(cQueue.deleteHead());
        System.out.println(cQueue.deleteHead());
        cQueue.appendTail(5);
        System.out.println(cQueue.deleteHead());
    }
}

/**
 * Your CQueue object will be instantiated and called as such:
 * CQueue obj = new CQueue();
 * obj.appendTail(value);
 * int param_2 = obj.deleteHead();
 */

