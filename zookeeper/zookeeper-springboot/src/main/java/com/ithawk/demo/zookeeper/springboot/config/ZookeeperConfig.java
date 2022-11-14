package com.ithawk.demo.zookeeper.springboot.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ithawk
 * @projectName zookeeper
 * @description: TODO
 * @date 2021/12/3114:08
 */
@Configuration
public class ZookeeperConfig {

    @Value("${zookeeper.address}")
    private String CONNECTION_STR;

    @Bean
    public CuratorFramework makeClient() {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(CONNECTION_STR)
                .sessionTimeoutMs(5000)
                //重试策略
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        //启动
        curatorFramework.start();
        return curatorFramework;
    }
}
