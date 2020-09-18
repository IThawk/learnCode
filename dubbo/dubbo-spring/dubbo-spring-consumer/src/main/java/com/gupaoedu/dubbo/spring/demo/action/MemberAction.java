package com.gupaoedu.dubbo.spring.demo.action;

import com.gupaoedu.dubbo.spring.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberAction {

    @Autowired
    private MemberService memberService;

    public String de() {
        String s = memberService.sayHelle();
        System.out.println("eeeee" + s);
        return s;
    }

}
