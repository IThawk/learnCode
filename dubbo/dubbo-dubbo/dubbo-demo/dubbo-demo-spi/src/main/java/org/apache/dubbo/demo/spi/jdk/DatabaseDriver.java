package org.apache.dubbo.demo.spi.jdk;

public interface DatabaseDriver<T> {

    String name();

    String buildConnect(String txt);

    String sayHello();

    T test(T t);
}
