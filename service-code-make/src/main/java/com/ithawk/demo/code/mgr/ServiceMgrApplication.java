package com.ithawk.demo.code.mgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;


/**
 * @Class: ServiceMgrApplication
 * @Package
 * @Description:
 */
@SpringBootApplication
//@EnableDiscoveryClient
//@RefreshScope
@EnableOpenApi //swagger
public class ServiceMgrApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceMgrApplication.class, args);
    }


}
