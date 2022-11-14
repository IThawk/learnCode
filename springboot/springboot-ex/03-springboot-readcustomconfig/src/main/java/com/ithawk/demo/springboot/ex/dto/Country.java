package com.ithawk.demo.springboot.ex.dto;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value="custom.properties", encoding = "utf-8")
@ConfigurationProperties("country")
@Data
public class Country {
    private String[] cities;
}
