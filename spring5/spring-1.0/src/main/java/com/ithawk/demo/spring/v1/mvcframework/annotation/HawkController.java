package com.ithawk.demo.spring.v1.mvcframework.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HawkController {
    String value() default "";
}
