package com.ithawk.demo.spring.aop.service;

import com.ithawk.demo.spring.aop.aspect.MyAopAnnotation;
import com.ithawk.demo.spring.aop.model.Member;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 注解版业务操作类
 * @author Tom
 */
@Service
public class MemberService {

	private final static Logger log = Logger.getLogger(MemberService.class);
	
	public Member get(long id){
		log.info("getMemberById method . . .");
		return new Member();
	}
	
	@MyAopAnnotation(value = "test")
	public Member get(){
		log.info("getMember method . . .");
		return new Member();
	}
	@MyAopAnnotation("小红")
	public void sayHello(String name) {
		System.out.println("hello, " + name);
	}
	public void save(Member member){
		log.info("save member method . . .");
	}
	
	public boolean delete(long id) throws Exception{
		log.info("delete method . . .");
		throw new Exception("spring aop ThrowAdvice演示");
	}
	
}
