package com.macro.mall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 *启用feign
 *  <p>
 *             <dependency>
 *                  <groupId>org.springframework.cloud</groupId>
 *                  <artifactId>spring-cloud-starter-openfeign</artifactId>
 *             </dependency>
 * </p>
 */
@EnableFeignClients
/**
 * 启用nacos注册中心
 * <p>
 *             <dependency>
 *             <groupId>com.alibaba.cloud</groupId>
 *             <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
 *         </dependency>
 *         <dependency>
 *             <groupId>com.alibaba.cloud</groupId>
 *             <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
 *         </dependency>
 * </p>
 */
@EnableDiscoveryClient
@SpringBootApplication()
public class MallAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallAuthApplication.class, args);
    }

}
