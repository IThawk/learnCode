package ${basePackage}.core;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @author ithawk
* Created by ${author} on ${date}.
* @projectName elasticsearch
* @description:
* @date ${date}
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

