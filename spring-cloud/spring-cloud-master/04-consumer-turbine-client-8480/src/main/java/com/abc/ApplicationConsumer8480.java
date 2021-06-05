package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients  // 开启Feign客户端
@SpringCloudApplication
public class ApplicationConsumer8480 {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConsumer8480.class, args);
    }

}
