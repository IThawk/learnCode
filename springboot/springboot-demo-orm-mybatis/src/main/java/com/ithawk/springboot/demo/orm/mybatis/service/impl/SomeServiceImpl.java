package com.ithawk.springboot.demo.orm.mybatis.service.impl;

import com.ithawk.springboot.demo.orm.mybatis.service.SomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("someService")
public class SomeServiceImpl implements SomeService {


    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(SomeServiceImpl.class);

    @Override
    public void sayHello() {
        logger.info(" com.ithawk.springboot.demo.orm.mybatis.service.impl.SomeServiceImpl.sayHello ");
    }
}
