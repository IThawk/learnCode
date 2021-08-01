package org.apache.dubbo.demo.spi.jdk;

public class MyDatabase implements DatabaseDriver{
    @Override
    public String name() {
        return "MyDatabase";
    }

    @Override
    public String buildConnect(String txt) {
        return "MyDatabase";
    }

    @Override
    public String sayHello() {
        return "MyDatabase";
    }

    @Override
    public Object test(Object o) {
        return "MyDatabase";
    }
}
