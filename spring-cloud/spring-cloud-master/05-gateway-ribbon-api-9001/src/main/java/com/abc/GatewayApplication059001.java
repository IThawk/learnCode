package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication059001 {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication059001.class, args);
    }

    @Bean
    public RouteLocator someRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(ps -> ps.path("/provider/depart/**")
                               .uri("lb://abcmsc-provider-depart")
                               .id("ribbon_route"))
                .build();
    }
}


