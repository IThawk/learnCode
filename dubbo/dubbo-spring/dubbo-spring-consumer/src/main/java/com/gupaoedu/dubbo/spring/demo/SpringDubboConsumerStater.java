package com.gupaoedu.dubbo.spring.demo;

import com.gupaoedu.dubbo.spring.demo.action.MemberAction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDubboConsumerStater {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext app = new ClassPathXmlApplicationContext("dubbo-consumer.xml");

        MemberAction memberAction =   app.getBean(MemberAction.class);
        memberAction.de();
        Thread.sleep(1000000000000000000L);
    }
}
