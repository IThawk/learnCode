package com.ithawk.dubbo.demo.spring.demo.action;


import com.ithawk.dubbo.demo.spring.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberAction {

    @Autowired
    private MemberService memberService;

    public String de() {
        String s = memberService.sayHello();
        System.out.println("调用到了服务" + s);
        return s;
    }

}
