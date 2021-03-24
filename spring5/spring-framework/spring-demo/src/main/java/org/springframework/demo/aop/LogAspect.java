package org.springframework.demo.aop;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;


public class LogAspect {

	Log log = LogFactory.getLog(LogAspect.class);

	public void before(JoinPoint joinPoint) throws Throwable {
		log.info("前置切面=》调用参数：" + JSON.toJSONString(joinPoint.getArgs()));
	}

	public void afterReturn(JoinPoint joinPoint, Object result) throws Throwable {
		log.info("后置返回切面=》调用参数:" + JSON.toJSONString(joinPoint.getArgs()) + "，返回值=》:" + JSON.toJSONString(result));
	}

	public void after(JoinPoint joinPoint) throws Throwable {
		log.info("后置切面=》调用参数：" + JSON.toJSONString(joinPoint.getArgs()));
	}

	public void afterThrow(JoinPoint joinPoint, Exception exception) throws Throwable {
		log.info("后置返回异常切面=》调用参数" + JSON.toJSONString(joinPoint.getArgs()) + "，异常信息=》:" + exception.getMessage());
	}
}
