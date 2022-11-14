package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GlobalApplication059000 {

    public static void main(String[] args) {
        SpringApplication.run(GlobalApplication059000.class, args);
    }

    @Bean
    public RouteLocator someRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(ps -> ps.path("/**")
                              .uri("http://localhost:8080")
                              .id("custom_filter_route"))
                .build();
    }

}
