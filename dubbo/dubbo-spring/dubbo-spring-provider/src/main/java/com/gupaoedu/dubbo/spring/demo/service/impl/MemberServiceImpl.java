package com.gupaoedu.dubbo.spring.demo.service.impl;

import com.gupaoedu.dubbo.spring.api.MemberService;
import org.springframework.stereotype.Service;

@Service("memberService")
public class MemberServiceImpl implements MemberService {


	@Override
	public String sayHelle() {
		return "hello";
	}
}
