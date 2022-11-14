package com.ithawk.mgr.core;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @author ithawk
* Created by CodeGenerator on 2021/12/03.
* @projectName elasticsearch
* @description:
* @date 2021/12/03
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

