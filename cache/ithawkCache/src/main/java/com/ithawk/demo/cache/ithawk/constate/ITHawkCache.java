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

    /**
     * @className ITHawkCache
     * @description:  根据入参生成 key的规则
     * <p>
     *
     * </p>
     * @author IThawk
     * @date 2021/8/13 8:13
     */
    String keyMaker() default "";

}
