package com.ithawk.dubbo.demo.spring.service.impl;

import com.ithawk.dubbo.demo.spring.api.MemberService;
//import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
//import org.springframework.stereotype.Service;

@Service("memberService")
//@DubboService
public class MemberServiceImpl implements MemberService {


	@Override
	public String sayHello() {
		return "hello";
	}
}
