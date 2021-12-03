package ${basePackage}.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @author ithawk
* Created by ${author} on ${date}.
* @description: ES
* @date ${date}
*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EsDocument {

    String indexName() default "";

    boolean useServerConfiguration() default false;

    short shards() default 1;

    short replicas() default 1;
}


