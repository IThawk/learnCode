package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer   // 开启Eureka服务
@SpringBootApplication
public class ApplicationEuerkaServer8300 {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationEuerkaServer8300.class, args);
    }

}
