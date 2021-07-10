package com.kkb.spring.aop.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kkb.spring.aop.target.UserService;

//@EnableAspectJAutoProxy

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-aop-anno.xml")
public class TestAOPAnno {

	@Autowired
	private UserService userService;
	
	@Test
	public void test() {
		userService.saveUser();
		System.out.println("=================");
		userService.saveUser("lisi");
		System.out.println("=================");
		userService.updateUser();
	}
}
