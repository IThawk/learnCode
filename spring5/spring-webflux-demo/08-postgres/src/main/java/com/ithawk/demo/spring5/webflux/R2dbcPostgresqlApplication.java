package com.ithawk.demo.spring5.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;


@EnableR2dbcRepositories
@SpringBootApplication
public class R2dbcPostgresqlApplication {


    public static void main(String[] args) {
        SpringApplication.run(R2dbcPostgresqlApplication.class, args);
    }

}
