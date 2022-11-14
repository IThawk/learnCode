package com.ithawk.demo.spring5.webflux.function;

import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class BiFunctionTest {

    @Test
    public void test01() {
        BiFunction<Integer, Integer, String> biFun = (x, y) -> x + " : " + y;
        System.out.println(biFun.apply(3, 5));   // 3 : 5
    }

    @Test
    public void test02() {
        BiFunction<Integer, Integer, String> biFun = (x, y) -> x + " : " + y;
        UnaryOperator<String> up = str -> "键值对为 " + str;
        // 将(3,5)应用于biFun上，再将biFun的运算结果作为up的参数进行运算
        System.out.println(biFun.andThen(up).apply(3, 5));    // 键值对为  3 : 5
    }

    @Test
    public void test03() {
        BiFunction<Integer, Integer, Integer> biFun = (x, y) -> x + y;
        Function<Integer, String> up = n -> "结果为 " + n;
        // 将(3,5)应用于biFun上，再将biFun的运算结果作为up的参数进行运算
        System.out.println(biFun.andThen(up).apply(3, 5));    // 结果为 8
    }

}
