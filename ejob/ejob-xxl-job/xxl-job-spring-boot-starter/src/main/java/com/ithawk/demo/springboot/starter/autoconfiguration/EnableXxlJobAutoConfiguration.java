package com.ithawk.demo.springboot.starter.autoconfiguration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({DataBaseTwoConfig.class})
public @interface EnableXxlJobAutoConfiguration {

}
