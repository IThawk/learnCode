package com.ithawk.demo.springboot.starter.format;

public interface FormatProcessor {

    //定义一个格式化的方法
    <T> String format(T obj);
}
