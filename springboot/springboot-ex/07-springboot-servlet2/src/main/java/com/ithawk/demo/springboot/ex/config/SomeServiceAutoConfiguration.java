package com.ithawk.demo.springboot.ex.config;


import com.ithawk.demo.springboot.ex.service.SomeService;
import com.ithawk.demo.springboot.ex.service.SomeServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 只有当前类路径下存在SomeService类时才会启用当前的JavaConfig配置类
@ConditionalOnClass(SomeService.class)
// 指定配置文件中指定属性的封装类
@EnableConfigurationProperties(SomeServiceProperties.class)
public class SomeServiceAutoConfiguration {
    @Autowired
    private SomeServiceProperties properties;

    // 以下两个方法的顺序不能颠倒
    @Bean
    @ConditionalOnProperty(name="some.service.enable", havingValue = "true", matchIfMissing = true)
    public SomeService someService() {
        return new SomeService(properties.getBefore(), properties.getAfter());
    }

    @Bean
    @ConditionalOnMissingBean
    public SomeService someService2() {
        return new SomeService("", "");
    }
}
