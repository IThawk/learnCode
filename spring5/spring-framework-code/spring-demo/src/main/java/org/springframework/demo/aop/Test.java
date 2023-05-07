package org.springframework.demo.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Test {

    String id() default "test";

    String className();

    String[] strings();
}
