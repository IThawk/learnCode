package com.ithawk.mybatis.demo.vip.aop.aspect;


import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.METHOD}) //基于Spring AOP的注解只能作用在方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MyAopAnnotation {
    String value();
}
