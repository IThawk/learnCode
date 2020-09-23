package com.gupao;

import com.ithawk.mybatis.demo.vip.spring5.demo.model.Member;
import com.ithawk.mybatis.demo.dubbo.spring.demo.action.MemberAction;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import reactor.core.publisher.Mono;

@SpringJUnitConfig({MemberAction.class})
public class Mytest {
    @Autowired
    MemberAction memberAction;

    @Test
    public void test(){
//        MemberAction memberAction = applicationContext.getBean(MemberAction.class);
        Mono<Member> s =  memberAction.getByName("lis");
        System.out.println("......");
    }
}
