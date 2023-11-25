package com.xxl.job.http.config;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * annotation for method jobhandler
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Component
public @interface XxlJobMethod {

    /**
     * jobhandler name
     */
    String value() default "";

    /**
     * init handler, invoked when JobThread init
     */
    String corn() default "";

    /**
     * d
     */
    String executorHandler() default "httpJobHandler";

    /**
     * dev:http://127.0.0.1:8080/test|uat:http://127.0.0.1:8080/test|prd:http://127.0.0.1:8080/test
     * 可调用方法基础路径：例如：baseUrl : http://127.0.0.1:8080/test   ->> 调用接口 http://127.0.0.1:8080/test/xxlJob/task/main
     */
    String baseUrl() default "";

    /**
     * // 调度状态：0-停止，1-运行
     *
     * @return
     */
    int triggerStatus() default 0;


    /**
     * 默认使用 GET,支持 POST
     * @return
     */
    String method() default "GET";

    /**
     * 请求参数
     * dev:{"a":"b"}|uat:{"a":"b"}|prd:{"a":"b"} 可调用方法基础路径：
     * 例如：baseUrl : http://127.0.0.1:8080/test ->> 调用接口 http://127.0.0.1:8080/test/xxlJob/task/main
     * @return
     */
    String reqBody() default "";

}
