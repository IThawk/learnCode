package com.xkcoding.orm.mybatis.plus;

import com.ithawk.demo.springboot.starter.autoconfiguration.EnableXxlJobAutoConfiguration;
import com.xxl.job.http.config.EnableXxlJobHttpHandlerAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p>
 * 启动器
 * </p>
 */

@EnableXxlJobHttpHandlerAutoConfiguration
@EnableXxlJobAutoConfiguration()
@EnableAsync
@SpringBootApplication
public class SpringBootXxlJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootXxlJobApplication.class, args);
    }
}
