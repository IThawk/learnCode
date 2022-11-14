package com.ithawk.demo.spring.v1.mvcframework.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HawkRequestParam {
    String value() default "";
}
