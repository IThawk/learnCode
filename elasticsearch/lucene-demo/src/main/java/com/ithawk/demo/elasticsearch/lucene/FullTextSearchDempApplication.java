package com.ithawk.demo.elasticsearch.lucene;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ithawk.demo.elasticsearch.lucene")
public class FullTextSearchDempApplication{
    public static void main(String[] args) {
        SpringApplication.run(FullTextSearchDempApplication.class, args);


        String s =new String();
        s.contains("ddd");
    }
}