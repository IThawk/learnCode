package com.ithawk.demo.springcloud.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCircuitBreaker //
@EnableFeignClients  // 开启Feign客户端
@EnableHystrixDashboard  // 开启Dashboard功能
@SpringBootApplication
public class CloudApplicationConsumer8080 {

    public static void main(String[] args) {
        SpringApplication.run(CloudApplicationConsumer8080.class, args);
    }

}
