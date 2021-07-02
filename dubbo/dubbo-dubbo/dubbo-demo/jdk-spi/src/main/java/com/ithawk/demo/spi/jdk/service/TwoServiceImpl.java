package com.ithawk.demo.spi.jdk.service;

public class TwoServiceImpl implements SomeService {
    @Override
    public void hello() {
        System.out.println("执行TwoServiceImpl的hello()方法");
    }
}
