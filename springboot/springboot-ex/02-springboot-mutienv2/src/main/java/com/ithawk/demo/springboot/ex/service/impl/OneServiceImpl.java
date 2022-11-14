package com.ithawk.demo.springboot.ex.service.impl;

import com.ithawk.demo.springboot.ex.service.SomeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class OneServiceImpl implements SomeService {
    @Override
    public String send() {
        return "dev--在模拟器上发送信息";
    }
}
