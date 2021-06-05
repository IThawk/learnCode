package com.ithawk.demo.springcloud.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// @EnableEurekaClient     // 仅限于注册中心是Eureka
@EnableDiscoveryClient  // 注册中心可以是任意的类型
@SpringBootApplication
public class ApplicationViaProvider8081 {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationViaProvider8081.class, args);
    }

}
