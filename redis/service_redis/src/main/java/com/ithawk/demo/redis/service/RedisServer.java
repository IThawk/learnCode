package com.ithawk.demo.redis.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;
import tk.mybatis.spring.annotation.MapperScan;

@EnableOpenApi //swagger
@SpringBootApplication
@MapperScan("com.ithawk.demo.redis.service.mapper") //import tk.mybatis.spring.annotation.MapperScan;
public class RedisServer
{

    public static void main(String[] args)
    {
        SpringApplication.run(RedisServer.class, args);
    }

}
