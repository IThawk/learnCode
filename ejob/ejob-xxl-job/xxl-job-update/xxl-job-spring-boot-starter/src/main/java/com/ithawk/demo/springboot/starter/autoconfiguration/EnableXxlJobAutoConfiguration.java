package com.ithawk.demo.springboot.starter.autoconfiguration;

import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({XxlJobAdminWebConfig.class})
@EnableAsync
public @interface EnableXxlJobAutoConfiguration {

}
