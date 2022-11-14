package com.ithawk.demo.spring5.webflux.function;

import org.junit.Test;

import java.lang.FunctionalInterface ;

/**
 * @author ithawk
 * @projectName spring-webflux-demo
 * @description:
 * @date 2022/4/1518:42
 */
public class MyFunctionTest {

    @FunctionalInterface
    interface MyFunction<T,U, R> {

        R rever(T t,U u);
    }

    @Test
    public void test01() {
        MyFunction<Integer, String,String> fun = (n,j) -> "我爱你，" + n;
        System.out.println(fun.rever(2019,""));
    }
}


