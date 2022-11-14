package com.ithawk.mgr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.util.ConcurrentLruCache;

/**
* @Class: ServiceApplication
* @Package
* @Description:
*/
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@EnableOpenApi //swagger
@MapperScan("com.ithawk.mgr")
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }


}
