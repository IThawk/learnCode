package com.xxl.job.http.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(XxlJobHttpProperties.class)
@ComponentScan(basePackages = {"com.xxl.job.http"})
public class XxlJobHttpHandlerConfig{

}
