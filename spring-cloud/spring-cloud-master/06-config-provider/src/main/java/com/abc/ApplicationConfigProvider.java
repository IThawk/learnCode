package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @EnableEurekaClient     // 仅限于注册中心是Eureka
// @EnableDiscoveryClient  // 注册中心可以是任意的类型
@SpringBootApplication
public class ApplicationConfigProvider {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfigProvider.class, args);
    }

}
