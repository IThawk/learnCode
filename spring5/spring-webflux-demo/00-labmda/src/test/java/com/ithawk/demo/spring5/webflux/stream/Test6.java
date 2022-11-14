package com.ithawk.demo.spring5.webflux.stream;

import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test6 {

    // 对于流的并行操作，forEach()处理结果是无序的
    @Test
    public void test01() {
        String words = "Beijing is the capital of China";
        Stream.of(words.split(" "))
                .parallel()
                .forEach(System.out::println);
    }

    // 对于流的并行操作，forEach()处理结果是有序的
    @Test
    public void test02() {
        String words = "Beijing is the capital of China";
        Stream.of(words.split(" "))
                .parallel()
                .forEachOrdered(System.out::println);
    }

    // 将流中的元素收集到一个集合中
    @Test
    public void test03() {
        String words = "Beijing is the capital of China";
        List<String> list = Stream.of(words.split(" "))
                .collect(Collectors.toList());
        System.out.println(list);
    }

    // 将流中的元素收集到一个数组中
    @Test
    public void test04() {
        String words = "Beijing is the capital of China";
        Object[] array = Stream.of(words.split(" "))
                .toArray();
        for(Object obj : array) {
            System.out.println(obj);
        }
    }

    // 统计流中元素的个数
    @Test
    public void test05() {
        String words = "Beijing is the capital of China";
        long count = Stream.of(words.split(" "))
                .count();
        System.out.println(count);  // 结果为6
    }

    // 计算流中所有单词的长度之和
    @Test
    public void test06() {
        String words = "Beijing is the capital of China";
        Optional<Integer> reduce = Stream.of(words.split(" "))
                .map(word -> word.length())
                .reduce((s1, s2) -> s1 + s2);
        // Optional的get()方法在其封装的对象为空时会抛出异常
        System.out.println(reduce.get());  // 结果为26
    }

    // 计算流中所有单词的长度之和
    @Test
    public void test07() {
        String words = "Beijing is the capital of China";
        Optional<Integer> reduce = Stream.of(words.split(" "))
                .map(word -> word.length())
                // .filter(n -> n > 200)
                .reduce((s1, s2) -> s1 + s2);
        // Optional的orElse()方法在正常情况下会返回正常的值
        // 当其封装的对象为空时会返回参数指定的值
        System.out.println(reduce.orElse(-1));
    }

    // 计算流中所有单词的长度之和
    @Test
    public void test08() {
        String words = "Beijing is the capital of China";
        Integer reduce = Stream.of(words.split(" "))
                .map(word -> word.length())
                // .filter(n -> n > 200)
                .reduce(0, (s1, s2) -> s1 + s2);
        System.out.println(reduce);
    }

    // 将流中的元素使用逗号相连接
    @Test
    public void test09() {
        String words = "Beijing is the capital of China";
        String reduce = Stream.of(words.split(" "))
                // .filter(n -> n.length() > 200)
                .reduce(null, (s1, s2) -> s1 + " , " + s2);
        System.out.println(reduce);
    }

    // 从流中找出长度最大的那个单词
    @Test
    public void test10() {
        String words = "Beijing is the capital of China";
        String max = Stream.of(words.split(" "))
                // .filter(s -> s.length() > 200)
                .max((s1, s2) -> s1.length() - s2.length())
                .orElse("当前流中没有元素");
        System.out.println(max);   // 结果为Beijing
    }

    // 从流中找出长度最小的那个单词
    @Test
    public void test11() {
        String words = "Beijing is the capital of China";
        String max = Stream.of(words.split(" "))
                // .filter(s -> s.length() > 200)
                .max((s1, s2) -> s2.length() - s1.length())
                .orElse("当前流中没有元素");
        System.out.println(max);   // 结果为is
    }

    // 从流中找出长度最小的那个单词
    @Test
    public void test12() {
        String words = "Beijing is the capital of China";
        String max = Stream.of(words.split(" "))
                // .filter(s -> s.length() > 200)
                .min((s1, s2) -> s1.length() - s2.length())
                .orElse("当前流中没有元素");
        System.out.println(max);   // 结果为is
    }

    // 从流中找出长度最大的那个单词
    @Test
    public void test13() {
        String words = "Beijing is the capital of China";
        String max = Stream.of(words.split(" "))
                // .filter(s -> s.length() > 200)
                .min((s1, s2) -> s2.length() - s1.length())
                .orElse("当前流中没有元素");
        System.out.println(max);   // 结果为Beijing
    }

    // 判断所有单词长度是否大于3
    // allMatch()：只要找到一个不符合条件的元素马上结束匹配工作
    // 本例即指，只要找到一个单词长度小于等于3的，马上结束匹配
    @Test
    public void test14() {
        String words = "Beijing is the capital of China";
        boolean allMatch = Stream.of(words.split(" "))
                .allMatch(word -> word.length() > 3);
        System.out.println(allMatch);   // 结果为false
    }

    // 判断是否存在单词长度大于3的单词
    // anyMatch()：只要找到一个符合条件的元素马上结束匹配工作，返回true
    // 本例即指，只要找到一个单词长度大于3的，马上结束匹配
    @Test
    public void test15() {
        String words = "Beijing is the capital of China";
        boolean anyMatch = Stream.of(words.split(" "))
                .anyMatch(word -> word.length() > 3);
        System.out.println(anyMatch);   // 结果为true
    }

    // 判断是否不存在单词长度大于3的单词
    // noneMatch()：只要找到一个符合条件的元素马上结束匹配工作，返回false
    // 本例即指，只要找到一个单词长度大于3的，马上结束匹配
    @Test
    public void test16() {
        String words = "Beijing is the capital of China";
        boolean noneMatch = Stream.of(words.split(" "))
                .noneMatch(word -> word.length() > 3);
        System.out.println(noneMatch);   // 结果为false
    }

    // findFirst()：只要找到第一个元素，马上结束查找
    @Test
    public void test17() {
        String words = "Beijing is the capital of China";
        String element = Stream.of(words.split(" "))
                .findFirst()
                .orElse("这里没有一个元素");
        System.out.println(element);
    }

    // findAny()：只要找到任何一个元素，马上结束查找
    @Test
    public void test18() {
        String words = "Beijing is the capital of China";
        String element = Stream.of(words.split(" "))
                .findAny()
                .orElse("这里没有一个元素");
        System.out.println(element);
    }
}
