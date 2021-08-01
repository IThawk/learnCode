package com.abc;

import com.abc.filter.AddHeaderGatewayFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication059000 {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication059000.class, args);
    }

    @Bean
    public RouteLocator someRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(ps -> ps.path("/**")
                                // 指定使用自定义的Filter
                              .filters(f -> f.filter(new AddHeaderGatewayFilter()))
                              .uri("http://localhost:8080")
                              .id("custom_filter_route"))
                .build();
    }

}


