package com.gpmall.user.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//定义包扫描位置
@ComponentScan(basePackages = {"com.gpmall.user", "com.gpmall.commons"})
//定义mapper文件的扫描位置
@MapperScan("com.gpmall.user.dal.persistence")
public class UserProviderApplication {

    public static void main(String[] args) {

        SpringApplication.run(UserProviderApplication.class, args);

    }

}
