package com.ithawk.demo.elasticsearch.cloud.core;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ithawk
 * @projectName elasticsearch
 * @description: TODO
 * @date 2021/12/38:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EsFiled {

    EsFieldType type() default EsFieldType.TEXT;

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    boolean index() default true;

    boolean highLight() default true;

    String searchAnalyzer() default "";

    String analyzer() default "";

    String normalizer() default "";

    String[] ignoreFields() default {};
}
