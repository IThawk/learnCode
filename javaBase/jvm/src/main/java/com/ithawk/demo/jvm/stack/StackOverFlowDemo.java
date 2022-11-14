package com.ithawk.demo.jvm.stack;

public class StackOverFlowDemo {

    public static long count=0;

    public static void method(long i){
        System.out.println(count++);
        method(i);
    }

    public static void main(String[] args) {
        method(1);
    }
}
