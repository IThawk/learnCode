package org.apache.dubbo.demo.spi.spring;

public class RedDatabase implements DatabaseDriver {
    @Override
    public String name() {
        return "RedDatabase";
    }

    @Override
    public String buildConnect(String txt) {
        return "RedDatabase";
    }

    @Override
    public String sayHello() {
        return "RedDatabase";
    }

    @Override
    public Object test(Object o) {
        return "RedDatabase";
    }
}
