package com.ithawk.demo.elasticsearch.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.oas.annotations.EnableOpenApi;


/**
 * @Class: ServiceSearchApplication
 * @Package com.itheima.controller
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@MapperScan("com.ithawk.demo.elasticsearch.cloud")
@EnableOpenApi //swagger
public class ServiceSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceSearchApplication.class, args);
    }


}
