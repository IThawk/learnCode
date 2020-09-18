package com.gupaoedu.sentinel.sentinelprovider;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/10-20:19
 */
@Configuration
@DubboComponentScan("com.gupaoedu.sentinel")
public class DubboConfig {

    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig=new ApplicationConfig();
        applicationConfig.setName("sentinel-dubbo");
        applicationConfig.setOwner("Mic");
        return applicationConfig;
    }
    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig=new RegistryConfig();
        registryConfig.setAddress("zookeeper://192.168.13.106:2181");
        return registryConfig;
    }
    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig=new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20880);
        return protocolConfig;
    }
}
