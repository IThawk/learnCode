package com.ithawk.demo.cache.ithawk.constate;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author ithawk
 * @title: ITHawkCache
 * @projectName cache
 * @description: TODO
 * @date 2021/8/910:36
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ITHawkCache {

    String actionType() default "QUERY";

    Class<?> keyClass() default String.class;

    String valueMethod() default "makeITHawkCache";
//    Function<?,?> keyMethod() default new Function<String, String>() {
//        @Override
//        public String apply(String s) {
//            return null;
//        }
//    };



}
