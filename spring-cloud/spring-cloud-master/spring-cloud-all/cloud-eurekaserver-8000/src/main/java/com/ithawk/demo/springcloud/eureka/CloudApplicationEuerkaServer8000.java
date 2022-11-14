package com.ithawk.demo.springcloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer   // 开启Eureka服务
@SpringBootApplication
public class CloudApplicationEuerkaServer8000 {

    public static void main(String[] args) {
        SpringApplication.run(CloudApplicationEuerkaServer8000.class, args);
    }

}
