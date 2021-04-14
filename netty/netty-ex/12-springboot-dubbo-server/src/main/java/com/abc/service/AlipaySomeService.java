package com.abc.service;

import org.springframework.stereotype.Service;

@Service
public class AlipaySomeService implements SomeService {
    @Override
    public String hello(String name) {
        return "开课吧欢迎你，" + name + " AlipaySomeService";
    }
}
