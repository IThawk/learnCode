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
public class ApplicationViaConsumer8080 {

    public static void main(String[] args) {
        try {
            Thread.sleep(-1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SpringApplication.run(ApplicationViaConsumer8080.class, args);
    }

}
