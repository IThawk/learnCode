package com.ithawk.mybatis.demo.dubbo.spring.demo.service.impl;

import com.ithawk.mybatis.demo.dubbo.spring.api.MemberService;
import org.springframework.stereotype.Service;

@Service("memberService")
public class MemberServiceImpl implements MemberService {


	@Override
	public String sayHelle() {
		return "hello";
	}
}
