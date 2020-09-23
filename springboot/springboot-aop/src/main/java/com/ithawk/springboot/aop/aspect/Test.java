package com.ithawk.springboot.aop.aspect;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.List;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Test {

    String id() default "test";

    String className();

    String[] strings();
}
