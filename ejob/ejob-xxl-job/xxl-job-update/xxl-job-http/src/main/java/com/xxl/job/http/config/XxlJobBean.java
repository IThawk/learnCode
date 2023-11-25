package com.xxl.job.http.config;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * annotation for method jobhandler
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Component
public @interface XxlJobBean {

    /**
     * jobhandler name
     */
    String value();

    /**
     * init handler, invoked when JobThread init
     */
    String corn() default "0 * * * * ?";

    /**
     * d
     */
    String executorHandler() default "httpJobHandler";


    /**
     * dev:http://127.0.0.1:8080/test|uat:http://127.0.0.1:8080/test|prd:http://127.0.0.1:8080/test
     * 可调用方法基础路径：例如：baseUrl : http://127.0.0.1:8080/test   ->> 调用接口 http://127.0.0.1:8080/test/xxlJob/task/main
     */
    String baseUrl() default "";

}
