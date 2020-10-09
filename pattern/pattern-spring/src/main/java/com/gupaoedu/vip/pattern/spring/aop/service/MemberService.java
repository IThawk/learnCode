package com.gupaoedu.vip.pattern.spring.aop.service;


import com.gupaoedu.vip.pattern.spring.aop.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 注解版业务操作类
 * @author Tom
 */
@Service
@Slf4j
public class MemberService {

	
	public Member get(long id){
		log.info("getMemberById method . . .");
		return new Member();
	}
	
	
	public Member get(){
		log.info("getMember method . . .");
		return new Member();
	}
	
	public void save(Member member){
		log.info("save member method . . .");
	}
	
	public boolean delete(long id) throws Exception{
		log.info("delete method . . .");
		throw new Exception("spring aop ThrowAdvice演示");
	}
	
}
