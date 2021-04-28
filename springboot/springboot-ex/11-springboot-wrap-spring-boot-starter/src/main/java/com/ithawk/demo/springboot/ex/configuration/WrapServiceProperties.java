package com.ithawk.demo.springboot.ex.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("wrap.service")
public class WrapServiceProperties {
    // 加载配置文件中的wrap.service.prefix与wrap.service.suffix
    private String prefix;
    private String suffix;
}
