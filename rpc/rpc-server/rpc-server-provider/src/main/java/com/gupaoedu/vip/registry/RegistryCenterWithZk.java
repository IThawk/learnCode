package com.gupaoedu.vip.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class RegistryCenterWithZk implements IRegistryCenter{

    CuratorFramework curatorFramework =null;

    {
        //初始化zookeeper的连接， 会话超时时间是5s，衰减重试
        curatorFramework = CuratorFrameworkFactory.builder().
                connectString(ZkConfig.CONNECTION_STR).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).
                namespace("registry")
                .build();
        curatorFramework.start();
    }


    @Override
    public void registry(String serviceName, String serviceAddress) {
        String servicePath="/"+serviceName;
        try {
            //判断节点是否存在
            if(curatorFramework.checkExists().forPath(servicePath)==null){
                curatorFramework.create().creatingParentsIfNeeded().
                        withMode(CreateMode.PERSISTENT).forPath(servicePath);
            }
            //serviceAddress: ip:port
            String addressPath=servicePath+"/"+serviceAddress;
            curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(addressPath);
            System.out.println("服务注册成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
