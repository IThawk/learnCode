package com.ithawk.demo.spring.v1.mvcframework.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HawkService {
    String value() default "";
}
