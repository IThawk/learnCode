package com.ithawk.springboot.aop;

import com.ithawk.springboot.aop.service.TestService;
import com.ithawk.springboot.aop.service.TestServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootAopTests {

    @Autowired
    TestServiceImpl testService;
    @Test
    public void contextLoads() {
        testService.test3("12121");
    }

}
