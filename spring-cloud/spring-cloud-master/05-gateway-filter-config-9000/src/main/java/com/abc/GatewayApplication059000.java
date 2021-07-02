package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication059000 {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication059000.class, args);
    }

    // @Bean
    // // 限流键解析器
    // public KeyResolver keyResolver() {
    //     return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    // }

}


