package com.gupaoedu.vip.pattern.spring.aop.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Tom.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnotationAspectTest {

    @Autowired MemberService memberService;

    @Test
    public void testGet(){
        memberService.get();
    }
}
