package com.ithawk.mybatis.demo.spi;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/24-21:04
 */
public interface DatabaseDriver<T> {

    String name();

    String buildConnect(String txt);

    String sayHello();

    T test(T t);
}
