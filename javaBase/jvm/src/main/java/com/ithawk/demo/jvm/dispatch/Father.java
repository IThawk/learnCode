package com.ithawk.demo.jvm.dispatch;

import java.util.HashMap;
import java.util.Map;

// 被调用的父类
public class Father {

    public class Father1 {

    }
    private static Map<String,String> stringMap ;
    static {

        System.out.println("father-static");
        stringMap = new HashMap<>();
    }

    public Father() {
        System.out.println("father");
    }

    public void f1() {
        stringMap.put("122111","222222");
        System.out.println("father-f1()");
    }

    public void f2() {
//        stringMap.put("122111","222222")
        System.out.println("father-f1()"+stringMap.get("122111"));
    }
    public void f1(int i) {
        System.out.println("father-f1() para-int " + i);
    }
}
