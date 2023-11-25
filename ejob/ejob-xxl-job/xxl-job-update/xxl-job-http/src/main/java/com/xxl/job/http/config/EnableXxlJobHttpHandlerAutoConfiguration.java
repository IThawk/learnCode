package com.xxl.job.http.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({XxlJobHttpHandlerConfig.class})
public @interface EnableXxlJobHttpHandlerAutoConfiguration {

}
