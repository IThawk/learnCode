package com.ithawk.demo.mybatis.sharding.v1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.ithawk.demo.mybatis.sharding.v1.mapper")
@SpringBootApplication
public class TlShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(TlShopApplication.class,args);
    }

}

