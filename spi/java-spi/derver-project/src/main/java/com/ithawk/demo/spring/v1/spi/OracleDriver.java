package com.ithawk.demo.spring.v1.spi;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/24-21:07
 */
public class OracleDriver implements DatabaseDriver{

    @Override
    public String name() {
        return "Oracle";
    }

    @Override
    public String buildConnect(String s) {
        return "Oracle Driver:"+s;
    }

    @Override
    public String sayHello() {
        return "HELLO ORACLE";
    }

    @Override
    public Object test(Object o) {
        return null;
    }
}