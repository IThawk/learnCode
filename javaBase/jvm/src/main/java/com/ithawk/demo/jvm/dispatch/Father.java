package com.ithawk.demo.jvm.dispatch;

// 被调用的父类
public class Father {
    static {

        System.out.println("father-static");
    }

    public Father() {
        System.out.println("father");
    }

    public void f1() {
        System.out.println("father-f1()");
    }

    public void f1(int i) {
        System.out.println("father-f1() para-int " + i);
    }
}
