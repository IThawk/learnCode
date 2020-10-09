package com.gupaoedu.vip.pattern.spring.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * 获取参数Aspect切面Bean
 * @author Tom
 */
//声明这是一个组件
@Component
//声明这是一个切面Bean
@Aspect
@Slf4j
public class ArgsAspect {

	//配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(* com.gupaoedu.vip.pattern.spring.aop.service..*(..))")
	public void aspect(){	}
	
	//配置前置通知,拦截返回值为cn.ysh.studio.spring.mvc.bean.User的方法
	@Before("execution(com.gupaoedu.vip.pattern.spring.aop.model.Member com.gupaoedu.vip.pattern.spring.aop.service..*(..))")
	public void beforeReturnUser(JoinPoint joinPoint){
		log.info("beforeReturnUser " + joinPoint);
	}

	//配置前置通知,拦截参数为cn.ysh.studio.spring.mvc.bean.User的方法
	@Before("execution(* com.gupaoedu.vip.pattern.spring.aop.service..*(com.gupaoedu.vip.pattern.spring.aop.model.Member))")
	public void beforeArgUser(JoinPoint joinPoint){
		log.info("beforeArgUser " + joinPoint);
	}

	//配置前置通知,拦截含有long类型参数的方法,并将参数值注入到当前方法的形参id中
	@Before("aspect()&&args(id)")
	public void beforeArgId(JoinPoint joinPoint, long id){
		log.info("beforeArgId " + joinPoint + "\tID:" + id);
	}
	
}