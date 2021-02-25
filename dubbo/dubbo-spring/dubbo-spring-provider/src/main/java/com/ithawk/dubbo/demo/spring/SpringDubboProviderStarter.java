package com.ithawk.dubbo.demo.spring;

import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;


public class SpringDubboProviderStarter {

    public static void main(String[] args) throws InterruptedException {
        //启动dubbo发布服务
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("dubbo-provider.xml");
//        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(ProviderConfiguration.class);

        app.start();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Configuration
    @EnableDubbo(scanBasePackages = "com.ithawk.dubbo.demo.spring")
    @PropertySource("classpath:/dubbo-provider.properties")
    static class ProviderConfiguration {
        @Bean
        public RegistryConfig registryConfig() {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress("zookeeper://127.0.0.1:12181");
            return registryConfig;
        }
    }

}
