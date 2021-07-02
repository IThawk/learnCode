package com.ithawk.demo.spi.jdk.service;

public class OneServiceImpl implements SomeService {
    @Override
    public void hello() {
        System.out.println("执行OneServiceImpl的hello()方法");
    }
}
