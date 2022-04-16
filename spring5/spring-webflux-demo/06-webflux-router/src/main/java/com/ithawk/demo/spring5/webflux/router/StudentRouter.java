package com.ithawk.demo.spring5.webflux.router;

import com.ithawk.demo.spring5.webflux.controller.StudentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

/**
 * 路由器
 */
@Configuration
public class StudentRouter {

    @Bean
    RouterFunction<ServerResponse> customRouter(StudentHandler handler) {
        return RouterFunctions.nest(RequestPredicates.path("/student"),
                                    RouterFunctions.route(RequestPredicates.GET("/all"), handler::findAllHandler)
                                                   .andRoute(RequestPredicates.POST("/save")
                                                             .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)), handler::saveHandler)
                                                   .andRoute(RequestPredicates.DELETE("/del/{id}"), handler::deleteHandler)
                                                   .andRoute(RequestPredicates.PUT("/update/{id}"), handler::updateHandler)
        );
    }


}
