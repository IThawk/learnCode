package com.ithawk.demo.spring5.webflux.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test5 {

    @Test
    public void test01() {
        String words = "Beijing welcome you I love China";
        Stream.of(words.split(" "))    // 当前流中的元素为各个单词
              .peek(System.out::print)
              .map(word -> word.length())   // 当前流中的元素为各个单词的长度
              .forEach(System.out::println);
    }

    @Test
    public void test02() {
        IntStream.range(1, 10)
                .mapToObj(i -> "No." + i)
                .forEach(System.out::println);
    }

    @Test
    public void test03() {
        String[] nums = {"111", "222", "333"};
        Arrays.stream(nums)   // "111", "222", "333"
                .mapToInt(Integer::valueOf)    // 111, 222, 333
                .forEach(System.out::println);
    }

    @Test
    public void test04() {
        String words = "Beijing welcome you I love China";
        Stream.of(words.split(" "))    // 当前流中的元素为各个单词
                .flatMap(word -> word.chars().boxed())
                .forEach(ch -> System.out.print((char)(ch.intValue())));
    }

    @Test
    public void test05() {
        String words = "Beijing welcome you I love China";
        Stream.of(words.split(" "))    // 当前流中的元素为各个单词
                // 最终形成的流中的元素为各个单词的字母
                .flatMap(word -> Stream.of(word.split("")))
                .forEach(System.out::print);
    }

    @Test
    public void test06() {
        String words = "Beijing welcome you I love China";
        Stream.of(words.split(" "))    // 当前流中的元素为各个单词
                // 当过滤条件为true时，当前元素会保留在流中，否则从流中删除
               .filter(word -> word.length() > 4)
               .forEach(System.out::println);
    }

    @Test
    public void test07() {
        String words = "Beijing welcome you I love China";
        Stream.of(words.split(" "))    // 当前流中的元素为各个单词
                .flatMap(word -> Stream.of(word.split("")))
                .distinct()   // 去除重复的字母
                .forEach(System.out::print);
    }

    @Test
    public void test08() {
        String words = "Beijing welcome you I love China";
        Stream.of(words.split(" "))    // 当前流中的元素为各个单词
                .flatMap(word -> Stream.of(word.split("")))
                .distinct()   // 去除重复的字母
                .sorted()   // 按照字典序进行排序
                .forEach(System.out::print);
    }

    @Test
    public void test09() {
        String words = "Beijing welcome you I love China";
        Stream.of(words.split(" "))    // 当前流中的元素为各个单词
                .flatMap(word -> Stream.of(word.split("")))
                .distinct()   // 去除重复的字母
                // 按照逆字典序进行排序
                .sorted((d1, d2) -> (int)d2.charAt(0) - (int)d1.charAt(0))
                .forEach(System.out::print);
    }

    @Test
    public void test10() {
        String words = "Beijing welcome you I love China";
        Stream.of(words.split(" "))    // 当前流中的元素为各个单词
                .flatMap(word -> Stream.of(word.split("")))
                .distinct()   // 去除重复的字母
                .sorted()
                .skip(4)      // 指定跳过（去除）4个元素
                .forEach(System.out::print);
    }

    @Test
    public void test11() {
        String words = "Beijing welcome you I love China";
        Stream.of(words.split(" "))    // 当前流中的元素为各个单词
                .skip(3)      // 指定跳过（去除）3个元素
                .forEach(System.out::print);
    }

    @Test
    public void test12() {
        String words = "Beijing welcome you I love China";
        Stream.of(words.split(" "))    // 当前流中的元素为各个单词
                .flatMap(word -> Stream.of(word.split("")))
                .distinct()   // 去除重复的字母
                .peek(System.out::print)
                .sorted()   // 按照字母序进行排序
                .forEach(System.err::print);
    }
}
