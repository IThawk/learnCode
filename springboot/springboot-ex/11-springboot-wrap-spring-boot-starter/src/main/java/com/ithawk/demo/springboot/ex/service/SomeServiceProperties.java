package com.ithawk.demo.springboot.ex.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 该类用于封装从配置文件中读取的属性值
 */
@Data
@ConfigurationProperties("some.service")
public class SomeServiceProperties {
    // 对应配置文件中的some.service.before属性
    private String before;

    // 对应配置文件中的some.service.after属性
    private String after;
}
