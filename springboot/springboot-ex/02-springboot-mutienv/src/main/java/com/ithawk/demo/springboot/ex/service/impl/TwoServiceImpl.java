package com.ithawk.demo.springboot.ex.service.impl;

import com.ithawk.demo.springboot.ex.service.SomeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("pro")
public class TwoServiceImpl implements SomeService {
    @Override
    public String send() {
        return "pro-调用短信运营商接口发送信息";
    }
}
