package com.ithawk.demo.springboot.ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching                // 开启缓存
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
