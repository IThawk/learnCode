package com.gupaoedu.vip.aop.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 获取参数Aspect切面Bean
 *
 * @author Tom
 */
//声明这是一个组件
@Component
//声明这是一个切面Bean
@Aspect
public class ArgsAspect {

    private final static Logger log = Logger.getLogger(ArgsAspect.class);

    //配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
    @Pointcut("execution(* com.gupaoedu.vip.aop.service..*(..))")
    public void aspect() {
    }

    //配置前置通知,拦截返回值为com.gupaoedu.vip.model.Member的方法
    @Before("execution(com.gupaoedu.vip.model.Member com.gupaoedu.vip.aop.service..*(..))")
    public void beforeReturnUser(JoinPoint joinPoint) {
        log.info("beforeReturnUser " + joinPoint);
    }

    //配置前置通知,拦截参数为com.gupaoedu.vip.model.Member的方法
    @Before("execution(* com.gupaoedu.vip.aop.service..*(com.gupaoedu.vip.model.Member))")
    public void beforeArgUser(JoinPoint joinPoint) {
        log.info("beforeArgUser " + joinPoint);
    }

    //配置前置通知,拦截含有long类型参数的方法,并将参数值注入到当前方法的形参id中
    @Before("aspect()&&args(id)")
    public void beforeArgId(JoinPoint joinPoint, long id) {
        log.info("beforeArgId " + joinPoint + "\tID:" + id);
    }


    /**
     * @Before 表示在方法执行前运行切面逻辑
     * value值定义了切点，此次使用注解来指定切点
     * @annotation（参数） 如果自定义的注解不需要参数，则可以为@annotation(类名)
     * && 连接多个指定切点的条件
     * args(name) 用来获取被注解的方法的参数
     */
    @Before(value = "@annotation(myAopAnnotation) && args(name)")
    public void dofore(JoinPoint joinPoint, MyAopAnnotation myAopAnnotation, String name) {
        System.out.println("the girl is " + myAopAnnotation.value());
        System.out.println("she will say hello to " + name);
    }

    /**
     * @Before 表示在方法执行前运行切面逻辑
     * value值定义了切点，此次使用注解来指定切点
     * @annotation（参数） 如果自定义的注解不需要参数，则可以为@annotation(类名)
     * && 连接多个指定切点的条件
     * args(name) 用来获取被注解的方法的参数
     */
    @Before(value = "@annotation(myAopAnnotation) ")
    public void dofore(JoinPoint joinPoint, MyAopAnnotation myAopAnnotation) {
        System.out.println("the myAopAnnotation is " + myAopAnnotation.value());
    }
}