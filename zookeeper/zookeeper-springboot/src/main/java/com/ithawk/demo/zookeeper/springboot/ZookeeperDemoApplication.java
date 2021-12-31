package com.ithawk.demo.zookeeper.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ithawk
 * @projectName zookeeper
 * @description: TODO
 * @date 2021/12/3114:09
 */
@SpringBootApplication
@MapperScan("com.ithawk.demo.zookeeper.springboot.mapper")
public class ZookeeperDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZookeeperDemoApplication.class, args);
    }
}
