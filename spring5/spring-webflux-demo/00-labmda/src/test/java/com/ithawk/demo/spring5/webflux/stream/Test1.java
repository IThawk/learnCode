package com.ithawk.demo.spring5.webflux.stream;

import org.junit.Test;

import java.util.stream.IntStream;

public class Test1 {

    // 静态方法，对指定元素进行乘方
    public static int compute(int n) {
        System.out.println(n + "进行乘方");
        return n * n;
    }

    @Test
    public void test01() {
        int[] nums = {1,2,3};
        int sum = IntStream.of(nums)
                        .map(i -> i *2)   // 中间操作  {2,4,6}
                        .map(i -> i * i)  // 中间操作  {4,16,36}
                        .sum();           // 终止操作
        System.out.println(sum);
    }

    @Test
    public void test02() {
        int[] nums = {1,2,3};
        int sum = IntStream.of(nums)
                        .map(i -> i *2)   // 中间操作  {2,4,6}
                        .map(i -> {
                            System.out.println(i + "进行乘方");
                            return i * i;
                        })  // 中间操作  {4,16,36}
                        .sum();           // 终止操作
        System.out.println(sum);
    }

    @Test
    public void test03() {
        int[] nums = {1,2,3};
        int sum = IntStream.of(nums)
                .map(i -> i *2)       // 中间操作  {2,4,6}
                .map(Test1::compute)  // 中间操作  {4,16,36}
                .sum();               // 终止操作
        System.out.println(sum);
    }

    @Test
    public void test04() {
        int[] nums = {1,2,3};
        IntStream.of(nums)
                .map(i -> i *2)       // 中间操作  {2,4,6}
                .map(Test1::compute);  // 中间操作  {4,16,36}
    }

}
