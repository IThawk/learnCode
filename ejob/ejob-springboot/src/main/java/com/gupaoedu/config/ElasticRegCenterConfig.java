package com.gupaoedu.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: qingshan
 * @Date: 2019/9/7 15:35
 * @Description: 咕泡学院，只为更好的你
 */
@Configuration
public class ElasticRegCenterConfig {
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(
            @Value("${regCenter.serverList}") final String serverList,
            @Value("${regCenter.namespace}") final String namespace) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
    }
}
