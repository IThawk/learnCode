package com.ithawk.demo.spring.aop.test;

import com.ithawk.demo.spring.aop.model.Member;
import com.ithawk.demo.spring.aop.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AnnotationTest {
	@Autowired
	MemberService memberService;
//	@Autowired ApplicationContext app;
	
	
	@Test
//	@Ignore
	public void test(){
		System.out.println("=====这是一条华丽的分割线======");
		
//		AnnotaionAspect aspect = app.getBean(AnnotaionAspect.class);
//		System.out.println(aspect);
		memberService.save(new Member());
//
		System.out.println("=====这是一条华丽的分割线======");
		try {
			memberService.delete(1L);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
}
