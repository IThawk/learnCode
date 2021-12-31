package com.ithawk.demo.mybatis.sharding.v1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan(basePackages = "com.ithawk.demo.mybatis.sharding.v1.mapper")
@SpringBootApplication
@EnableSwagger2
public class TlShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(TlShopApplication.class, args);
    }

}

