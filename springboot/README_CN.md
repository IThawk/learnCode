# springboot-aop springboot AOP使用
```java
package com.ithawk.springboot.aop.aspect;

import com.alibaba.fastjson.JSON;
import com.ithawk.springboot.aop.service.TestService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    Logger logger = LoggerFactory.getLogger(AnnotaionAspect1.class);
    
    @Autowired
    private TestService testService;

    //配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点(添加@Test)
    @Pointcut("@annotation(com.ithawk.springboot.aop.aspect.Test)")
    public void aspect() {
    }

    //配置前置通知
    @Before("aspect()")
    public void beforeReturnUser(JoinPoint joinPoint) {

        logger.info("beforeReturnUser " + JSON.toJSONString(joinPoint.getArgs()));
        testService.test();
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
            logger.info(s2);
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
        logger.info(JSON.toJSONString(joinPoint.getArgs()));
        logger.info(s1);
        logger.info("aroundReturnUser " + joinPoint);
        return re;
    }


    @After("aspect()")
    public void afterReturnUser(JoinPoint joinPoint) {

        logger.info("beforeReturnUser " + JSON.toJSONString(joinPoint.getArgs()));
        testService.test();
    }

    @AfterThrowing(value = "aspect()", throwing = "exception")
    public void afterExceptionUser(JoinPoint joinPoint, Exception exception) {
        logger.info("beforeReturnUser " + JSON.toJSONString(joinPoint.getArgs()));
        logger.info("beforeReturnUser " + JSON.toJSONString(joinPoint.getArgs()));
        testService.test();
    }

    @AfterReturning(returning = "returnValue", value = "aspect()")
    public Object afterReturnUser(JoinPoint joinPoint, Object returnValue) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Object re = null;
        logger.info("aroundReturnUser " + returnValue);
        return re;
    }


    //配置前置通知,com.ithawk.springboot.aop.service 拦截参数为 com.ithawk.springboot.aop.model.Member的方法
    @Before("execution(* com.ithawk.springboot.aop.service..*(com.ithawk.springboot.aop.model.Member))")
    public void beforeArgUser(JoinPoint joinPoint) {
        System.out.println("beforeArgUser " + joinPoint);
    }

    //配置前置通知,拦截含有long类型参数的方法,并将参数值注入到当前方法的形参id中
    @Before("aspect()&&args(id)")
    public void beforeArgId(JoinPoint joinPoint, long id) {
        logger.info("beforeArgId " + joinPoint + "\tID:" + id);
    }

}
```
## 说明：
* @Component  //声明这是一个组件
* @Aspect //声明这是一个切面Bean
* @Pointcut  // 设置切点
# SpringBoot

## SpringBoot中CommandLineRunner的作用
    平常开发中有可能需要实现在项目启动后执行的功能，
    SpringBoot提供的一种简单的实现方案就是添加一个model并实现CommandLineRunner接口，实现功能的代码放在实现的run方法中
### 如果有多个类实现CommandLineRunner接口，如何保证顺序
    SpringBoot在项目启动后会遍历所有实现CommandLineRunner的实体类并执行run方法，
     如果需要按照一定的顺序去执行，那么就需要在实体类上使用一个@Order注解（或者实现Order接口）来表明顺序@Order 注解的执行优先级是按value值从小到大顺序。
     CommandLineRunner和ApplicationRunner的执行顺序：
     在spring boot程序中，我们可以使用不止一个实现CommandLineRunner和ApplicationRunner的bean。
     为了有序执行这些bean的run()方法，可以使用@Order注解或Ordered接口。
     例子中我们创建了两个实现CommandLineRunner接口的bean和两个实现ApplicationRunner接口的bean。
     我们使用@Order注解按顺序执行这四个bean。
     如果是spring容器加载成功之后就需要实现 implements   ApplicationListener<ContextRefreshedEvent>
