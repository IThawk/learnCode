package com.ithawk.demo.netty.springboot.dubbo.service;

import org.springframework.stereotype.Service;

@Service
public class WechatSomeService implements SomeService {
    @Override
    public String hello(String name) {
        return "欢迎你，" + name + " WechatomeService";
    }
}
