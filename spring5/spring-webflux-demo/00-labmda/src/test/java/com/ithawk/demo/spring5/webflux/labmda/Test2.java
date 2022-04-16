package com.ithawk.demo.spring5.webflux.labmda;

import org.junit.Test;

@FunctionalInterface
interface Some2 {
    // 无参数有返回值
    String doSome();
}

public class Test2 {
    @Test
    public void test01() {
        Some2 some2 = new Some2() {
            @Override
            public String doSome() {
                return "匿名内部类";
            }
        };
        System.out.println(some2.doSome());
    }

    @Test
    public void test02() {
        Some2 some2 = () -> {
            System.out.println("使用Lambda实现");
            return "Lambda";
        };

        System.out.println(some2.doSome());
    }

    @Test
    public void test03() {
        Some2 some2 = () -> "Lambda";

        System.out.println(some2.doSome());
    }
}
