package com.ithawk.demo.spring.aop;

import com.ithawk.demo.spring.aop.service.MemberService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        //
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        applicationContext.start();
        MemberService memberService = applicationContext.getBean(MemberService.class);
        memberService.get();
        memberService.sayHello("ff");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
