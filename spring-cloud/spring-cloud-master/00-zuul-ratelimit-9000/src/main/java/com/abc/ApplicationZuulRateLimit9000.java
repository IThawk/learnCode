package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringCloudApplication
public class ApplicationZuulRateLimit9000 {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationZuulRateLimit9000.class, args);
    }


}
