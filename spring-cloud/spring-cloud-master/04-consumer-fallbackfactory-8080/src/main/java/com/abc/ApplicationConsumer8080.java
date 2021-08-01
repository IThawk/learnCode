package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


// @EnableCircuitBreaker  // 开启熔断器
// @SpringBootApplication
@EnableFeignClients  // 开启Feign客户端
@SpringCloudApplication
public class ApplicationConsumer8080 {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConsumer8080.class, args);
    }

}
