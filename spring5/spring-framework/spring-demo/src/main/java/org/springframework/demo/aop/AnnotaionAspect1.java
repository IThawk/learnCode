package org.springframework.demo.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 获取参数Aspect切面Bean
 *
 * @author ithawk
 */
//声明这是一个组件
@Component
//声明这是一个切面Bean
@Aspect
public class AnnotaionAspect1 {


	//配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点(添加@Test)
	@Pointcut("@annotation(org.springframework.demo.aop.Test)")
	public void aspect() {
	}

	//配置前置通知
	@Before("aspect()")
	public void beforeReturnUser(JoinPoint joinPoint) {

		System.out.println("beforeReturnUser " + JSON.toJSONString(joinPoint.getArgs()));
	}

	@Around("aspect()")
	public Object aroundReturnUser(ProceedingJoinPoint joinPoint) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
		Object re = null;
		try {
			re = joinPoint.proceed();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		Method s = ((MethodSignature) joinPoint.getSignature()).getMethod();
		String s1 = AnnotationUtils.findAnnotation(s, Test.class).id();
		String className = AnnotationUtils.findAnnotation(s, Test.class).className();
		String[] strings = AnnotationUtils.findAnnotation(s, Test.class).strings();
		for (String s2 : strings) {
			System.out.println(s2);
		}
		Class<?> clazz = Class.forName(className);
		Method[] method = clazz.getMethods();
		for (Method method1 : method) {
			if (method1.getName().equals("test1")) {
				String objects = (String) joinPoint.getArgs()[0];
				method1.invoke(clazz.newInstance(), objects);
				break;
			}

		}
		Method method1 = clazz.getMethod("test1", String.class);
		method1.invoke(clazz.newInstance(), joinPoint.getArgs()[0]);
		System.out.println(joinPoint.getArgs());
		System.out.println(s1);
		System.out.println("aroundReturnUser " + joinPoint);
		return re;
	}


	@After("aspect()")
	public void afterReturnUser(JoinPoint joinPoint) {

		System.out.println("beforeReturnUser " + JSON.toJSONString(joinPoint.getArgs()));
	}

	@AfterThrowing(value = "aspect()", throwing = "exception")
	public void afterExceptionUser(JoinPoint joinPoint, Exception exception) {
		System.out.println("beforeReturnUser " + JSON.toJSONString(joinPoint.getArgs()));
		System.out.println("beforeReturnUser " + JSON.toJSONString(joinPoint.getArgs()));
	}

	@AfterReturning(returning = "returnValue", value = "aspect()")
	public Object afterReturnUser(JoinPoint joinPoint, Object returnValue) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
		Object re = null;
		System.out.println("aroundReturnUser " + returnValue);
		return re;
	}


	//配置前置通知,拦截参数为 org.springframework.demo.bean.Employee的方法
	@Before("execution(* org.springframework.demo.service..*(org.springframework.demo.bean.Employee))")
	public void beforeArgUser(JoinPoint joinPoint) {
		System.out.println("beforeArgUser " + joinPoint);
	}

	//配置前置通知,拦截含有long类型参数的方法,并将参数值注入到当前方法的形参id中
	@Before("aspect()&&args(id)")
	public void beforeArgId(JoinPoint joinPoint, long id) {
		System.out.println("beforeArgId " + joinPoint + "\tID:" + id);
	}

}