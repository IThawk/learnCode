package com.ithawk.demo.springboot.ex.service.impl;

import com.ithawk.demo.springboot.ex.service.SomeService;
import org.springframework.stereotype.Service;

@Service
public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome() {
        System.out.println("执行SomeServiceImpl的doSome()方法");
    }
}
