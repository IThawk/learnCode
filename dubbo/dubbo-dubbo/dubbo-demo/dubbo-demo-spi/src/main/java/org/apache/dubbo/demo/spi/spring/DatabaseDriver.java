package org.apache.dubbo.demo.spi.spring;

public interface DatabaseDriver<T> {

    String name();

    String buildConnect(String txt);

    String sayHello();

    T test(T t);
}
