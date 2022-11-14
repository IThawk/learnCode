package com.ithawk.dubbo.demo.spring.demo;


import com.ithawk.dubbo.demo.spring.demo.action.MemberAction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDubboConsumerStater {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("dubbo-consumer.xml");
        app.start();
        MemberAction memberAction = app.getBean(MemberAction.class);
        memberAction.de();

    }
}
