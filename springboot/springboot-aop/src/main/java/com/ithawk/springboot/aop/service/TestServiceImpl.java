package com.ithawk.springboot.aop.service;

import com.ithawk.springboot.aop.aspect.Test;
import com.ithawk.springboot.aop.controller.TestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements  TestService{

    Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);
    @Override
    public String test( ) {
        logger.info("233333");
        return "null";
    }

    public String test1( String t ) {
        logger.info("233333");
        return "null";
    }

    @Test(id = "ddd",className="com.ithawk.springboot.aop.service.TestServiceImpl",strings = {"1","2"})
    public String test3( String t ) {
        logger.info("233333");
        return "null";
    }

}
