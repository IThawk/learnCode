package com.ithawk.demo.ejob.springboot.config;

import org.apache.shardingsphere.elasticjob.reg.base.RegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.h2.Driver;

/**
 * @Author: IThawk
 */
@Configuration
public class ElasticRegCenterConfig {
    @Bean(initMethod = "init")
    @ConditionalOnMissingClass("org.apache.shardingsphere.elasticjob.reg.base.RegistryCenter")
    public ZookeeperRegistryCenter regCenter(
            @Value("${elasticjob.reg-center.serverLists}") final String serverList,
            @Value("${elasticjob.reg-center.namespace}") final String namespace) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
    }
}
