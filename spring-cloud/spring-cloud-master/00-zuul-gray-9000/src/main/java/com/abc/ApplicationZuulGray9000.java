package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringCloudApplication
public class ApplicationZuulGray9000 {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationZuulGray9000.class, args);
    }


}
