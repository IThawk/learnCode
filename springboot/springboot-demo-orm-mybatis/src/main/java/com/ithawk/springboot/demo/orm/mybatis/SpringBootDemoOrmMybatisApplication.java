package com.ithawk.springboot.demo.orm.mybatis;

import com.ithawk.springboot.demo.orm.mybatis.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringBootDemoOrmMybatisApplication {

    @Autowired
    private ApplicationUtils applicationUtils;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoOrmMybatisApplication.class, args);
    }
}
