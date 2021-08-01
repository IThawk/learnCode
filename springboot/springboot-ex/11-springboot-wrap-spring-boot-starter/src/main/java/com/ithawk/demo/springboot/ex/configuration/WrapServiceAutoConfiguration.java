package com.ithawk.demo.springboot.ex.configuration;


import com.ithawk.demo.springboot.ex.service.WrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(WrapService.class)
@EnableConfigurationProperties(WrapServiceProperties.class)
public class WrapServiceAutoConfiguration {
    @Autowired
    private WrapServiceProperties properties;

    @Bean
    // @ConditionalOnProperty(name = "wrap.service.enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnProperty(name = "wrap.service.enabled", matchIfMissing = true)
    public WrapService wrapService() {
        return new WrapService(properties.getPrefix(), properties.getSuffix());
    }

    // 注意，这个@Bean方法不能放到上面那个@Bean方法之上
    @Bean
    @ConditionalOnMissingBean
    public WrapService wrapService2() {
        return new WrapService("", "");
    }

}
