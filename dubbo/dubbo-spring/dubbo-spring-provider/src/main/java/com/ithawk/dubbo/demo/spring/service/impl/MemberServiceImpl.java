package com.ithawk.dubbo.demo.spring.service.impl;

import com.ithawk.dubbo.demo.spring.api.MemberService;
import org.springframework.stereotype.Service;

@Service("memberService")
public class MemberServiceImpl implements MemberService {


	@Override
	public String sayHello() {
		return "hello";
	}
}
