package com.ithawk.dubbo.demo.spring.demo.mock;

import com.ithawk.dubbo.demo.spring.api.MemberService;

public class MemberServiceMock implements MemberService {
    @Override
    public String sayHello() {
        return "服务降级了";
    }
}
