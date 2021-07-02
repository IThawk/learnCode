package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication099001 {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication099001.class, args);
    }

    @Bean
    public RouteLocator someRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/**")//
                            .filters(f -> f.addRequestHeader("X-Request-red", "blue")
                                           .prefixPath("/info"))
                            .uri("http://localhost:8080")
                            .id("xxx_route"))
                .build();
    }

    // @Bean
    // public RouteLocator someRouteLocator(RouteLocatorBuilder builder) {
    //     ZonedDateTime time = LocalDateTime.now().minusDays(5).atZone(ZoneId.systemDefault());
    //     return builder.routes()
    //             .route("after_route", r -> r.after(time)
    //                                        .uri("http://www.baidu.com"))
    //             .build();
    // }

}
