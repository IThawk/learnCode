package com.ithawk.demo.spring5.webflux.labmda;

import org.junit.Test;

@FunctionalInterface
interface Some4 {
    String doSome(String a, int b);
    // 默认方法
    default void doOther(String a, int b) {
        System.out.println("执行默认方法 - " + a + b);
    }
}

public class Test4 {
    @Test
    public void test01() {
        // 调用抽象方法
        Some4 some4 = (a, b) -> a + b;
        System.out.println(some4.doSome("Hello, ", 2020));

        // 调用默认方法
        some4.doOther("hello ", 2019);
    }
}
