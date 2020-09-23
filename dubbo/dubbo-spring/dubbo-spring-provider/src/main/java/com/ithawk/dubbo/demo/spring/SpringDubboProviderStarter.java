package com.ithawk.dubbo.demo.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;


public class SpringDubboProviderStarter {

    public static void main(String[] args) throws InterruptedException {
        //启动dubbo发布服务
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("dubbo-provider.xml");
        app.start();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
