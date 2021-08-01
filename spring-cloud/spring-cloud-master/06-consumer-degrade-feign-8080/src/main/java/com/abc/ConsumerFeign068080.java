package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients  // 开启所有Feign客户端
@SpringBootApplication
public class ConsumerFeign068080 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerFeign068080.class, args);
    }

}











