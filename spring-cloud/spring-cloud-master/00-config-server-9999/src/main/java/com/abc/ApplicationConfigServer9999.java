package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer  // 开启Config的服务器功能
@SpringBootApplication
public class ApplicationConfigServer9999 {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfigServer9999.class, args);
    }


}
