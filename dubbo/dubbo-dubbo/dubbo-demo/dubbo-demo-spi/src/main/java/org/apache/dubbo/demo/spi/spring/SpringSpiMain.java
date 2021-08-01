package org.apache.dubbo.demo.spi.spring;

import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * Hello world!
 */
public class SpringSpiMain {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        //org.springframework.core.io.support.SpringFactoriesLoader.loadFactoryNames
        //	public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
        List<DatabaseDriver> serviceLoader = SpringFactoriesLoader.loadFactories(DatabaseDriver.class,null);
        for (DatabaseDriver databaseDriver : serviceLoader) {
            System.out.println(databaseDriver.getClass().getName());
            System.out.println(databaseDriver.buildConnect("test"));
            if (databaseDriver.name().equals("Mysql")){
                System.out.println(databaseDriver.sayHello());;
            }
        }
    }
}
