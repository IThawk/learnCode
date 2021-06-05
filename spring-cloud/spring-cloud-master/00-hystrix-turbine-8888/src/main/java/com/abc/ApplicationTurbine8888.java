package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableTurbine
@EnableHystrixDashboard
@SpringCloudApplication
public class ApplicationTurbine8888 {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTurbine8888.class, args);
    }

}
