package com.gupao;

import com.ithawk.demo.spring.v1.vip.spring5.demo.model.Member;
import com.ithawk.demo.spring.v1.dubbo.spring.demo.action.MemberAction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import reactor.core.publisher.Mono;

/**
 * Created by Tom.
 */
public class ApplicationContextTest {
    public static void main(String[] args) {

//        ClassPathResource res = new ClassPathResource("a.xml,b.xml");
//        ClassPathResource res =new ClassPathResource(new String[]{"a.xml","b.xml"});

        ApplicationContext app = new ClassPathXmlApplicationContext("classpath:application-context.xml");
        MemberAction  memberAction = app.getBean(MemberAction.class);
        Mono<Member>  s =  memberAction.getByName("lis");
        System.out.println("......");


    }
}
