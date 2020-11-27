package com.ithawk.demo.spring.v1.mvcframework.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HawkRequestMapping {
    String value() default "";
}
