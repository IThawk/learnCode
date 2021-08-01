package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringCloudApplication
public class ApplicationZuuFilter9000 {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationZuuFilter9000.class, args);
    }


}
