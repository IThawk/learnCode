package com.ithawk.demo.spring.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi //swagger
public class SpringkafkademoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringkafkademoApplication.class, args);
    }

}

