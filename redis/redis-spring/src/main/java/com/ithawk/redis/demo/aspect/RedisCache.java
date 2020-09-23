package com.ithawk.redis.demo.aspect;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisCache {

    String key() default "";
}
