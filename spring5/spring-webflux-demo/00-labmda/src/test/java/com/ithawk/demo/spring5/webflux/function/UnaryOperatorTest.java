package com.ithawk.demo.spring5.webflux.function;

import org.junit.Test;

import java.util.function.UnaryOperator;

public class UnaryOperatorTest {

    @Test
    public void test01() {
        UnaryOperator<String> fun = n -> "我爱你，" + n;
        System.out.println(fun.apply("北京"));
    }

    @Test
    public void test02() {
        UnaryOperator<Integer> fun1 = x -> x * 2;
        UnaryOperator<Integer> fun2 = x -> x * x;

        // 先将5作为fun1的参数，计算结果为10，
        // 再将fun1的计算结果10，作为fun2的参数再计算
        System.out.println(fun1.andThen(fun2).apply(5));   // 100

        // 先将5作为fun2的参数，计算结果为25，
        // 再将fun2的计算结果25，作为fun1的参数再计算
        System.out.println(fun1.compose(fun2).apply(5));   // 50
    }

    @Test
    public void test03() {
        System.out.println(UnaryOperator.identity().apply(5));   // 5
        System.out.println(UnaryOperator.identity().apply(3 * 8));   // 24
    }

}
