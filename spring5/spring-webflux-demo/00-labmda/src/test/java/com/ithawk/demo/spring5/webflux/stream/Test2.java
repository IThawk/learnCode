package com.ithawk.demo.spring5.webflux.stream;

import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test2 {

    // 使用数组创建流
    @Test
    public void test01() {
        int[] nums = {1,2,3};

        IntStream stream = IntStream.of(nums);
        IntStream stream1 = Arrays.stream(nums);
    }

    // 使用集合创建流
    @Test
    public void test02() {
        // 使用List创建流
        List<String> names = new ArrayList<>();
        Stream<String> stream = names.stream();

        // 使用Set创建流
        Set<String> cities = new HashSet<>();
        Stream<String> stream1 = cities.stream();
    }

    // 创建数字流
    @Test
    public void test03() {
        // 创建一个包含1,2,3的Stream
        IntStream stream = IntStream.of(1, 2, 3);

        // 创建一个包含[1,5)范围的Stream
        IntStream range1 = IntStream.range(1, 5);
        // 创建一个包含[1,5]范围的Stream
        IntStream range2 = IntStream.rangeClosed(1, 5);

        // new Random().ints()创建一个无限流
        // limit(5)限制流中元素个数为5个
        IntStream ints = new Random().ints().limit(5);
        // 与上面的写法等价
        IntStream ints1 = new Random().ints(5);
    }

    // 自定义流
    @Test
    public void test04() {
        // 首先生成一个无限流，然后又限定了元素个数
        Stream<Double> stream = Stream.generate(() -> Math.random())
                                      .limit(5);
    }

}
