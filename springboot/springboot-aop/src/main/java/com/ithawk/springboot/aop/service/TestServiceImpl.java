package com.ithawk.springboot.aop.service;

import com.ithawk.springboot.aop.aspect.Test;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements  TestService{
    @Override
    public String test( ) {
        System.out.println("233333");
        return "null";
    }

    public String test1( String t ) {
        System.out.println("233333");
        return "null";
    }

    @Test(id = "ddd",className="com.ithawk.springboot.aop.service.TestServiceImpl",strings = {"1","2"})
    public String test3( String t ) {
        System.out.println("233333");
        return "null";
    }

}
