package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer   // 开启Eureka服务
@SpringBootApplication
public class ApplicationConfigEuerkaServer {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfigEuerkaServer.class, args);
    }

}
