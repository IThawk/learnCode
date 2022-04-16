package com.ithawk.demo.spring5.webflux.labmda;

import org.junit.Test;

@FunctionalInterface
interface Some {
    // 无参数无返回值
    void doSome();
}

public class Test1 {
    @Test
    public void test01() {
        Some some = new Some() {

            @Override
            public void doSome() {
                System.out.println("使用匿名内部类实现");
            }
        };

        some.doSome();
    }

    @Test
    public void test02() {
        Some some = () -> {
            System.out.println("使用Lambda实现");
        };

        some.doSome();
    }
}
