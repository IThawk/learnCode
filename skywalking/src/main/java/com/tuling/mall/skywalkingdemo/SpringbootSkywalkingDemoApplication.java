package com.tuling.mall.skywalkingdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@MapperScan("com.tuling.mall.skywalkingdemo.dao")
public class SpringbootSkywalkingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSkywalkingDemoApplication.class, args);
    }

}
