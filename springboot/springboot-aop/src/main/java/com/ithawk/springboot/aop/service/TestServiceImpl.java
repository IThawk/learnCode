package com.ithawk.springboot.aop.service;

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

}
