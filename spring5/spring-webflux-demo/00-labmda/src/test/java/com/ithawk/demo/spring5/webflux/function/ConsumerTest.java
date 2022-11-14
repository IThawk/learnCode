package com.ithawk.demo.spring5.webflux.function;

import org.junit.Test;

import java.util.function.Consumer;

public class ConsumerTest {

    @Test
    public void test01() {
        Consumer<String> con = str -> System.out.println("Hello, " + str);
        con.accept("Tom");
    }

    @Test
    public void test02() {
        Consumer<Integer> con1 = n -> System.out.println(n * 2);
        Consumer<Integer> con2 = n -> System.out.println(n * n);

        con1.andThen(con2).accept(5);
    }



}
