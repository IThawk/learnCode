package com.ithawk.demo.spring.v1.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解： 自动注入注解
 * 说明： @Target({ElementType.FIELD}) 注解使用的作用
 * <p>
 * TYPE, 作用在类上面
 * <p>
 * FIELD, 作用在属性上面
 * <p>
 * METHOD, 作用在方法上面
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HawkAutowired {
    String value() default "";
}
