package org.apache.dubbo.demo.spi.jdk;

import java.util.ServiceLoader;

/**
 * Hello world!
 */
public class JDKSpiMain {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        //java.util.ServiceLoader.PREFIX = "META-INF/services/";
        ServiceLoader<DatabaseDriver> serviceLoader = ServiceLoader.load(DatabaseDriver.class);
        for (DatabaseDriver databaseDriver : serviceLoader) {
            System.out.println(databaseDriver.getClass().getName());
            System.out.println(databaseDriver.buildConnect("test"));
            if (databaseDriver.name().equals("Mysql")){
                System.out.println(databaseDriver.sayHello());;
            }
        }
    }
}
