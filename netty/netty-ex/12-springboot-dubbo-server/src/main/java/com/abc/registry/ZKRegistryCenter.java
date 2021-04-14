package com.abc.registry;

import com.abc.constant.ZKConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Component;

/**
 * zk做注册中心
 */
@Component
public class ZKRegistryCenter implements RegistryCenter {
    private CuratorFramework client;

    public ZKRegistryCenter() {
        // 创建并初始化zk的客户端
        client = CuratorFrameworkFactory.builder()
                // 指定要连接的zk集群
                .connectString(ZKConstant.ZK_CLUSTER)
                // 指定连接超时时限
                .connectionTimeoutMs(10000)
                // 指定会话超时时间
                .sessionTimeoutMs(4000)
                // 指定重试策略：每重试一次，休息1秒，最多重试10次
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        // 启动zk的客户端
        client.start();
    }

    @Override
    public void register(String serviceName, String serviceAddress) throws Exception {
        // 创建持久节点
        String servicePath = ZKConstant.ZK_DUBBO_ROOT_PATH + "/" + serviceName;
        // if (client.checkExists().forPath(servicePath) == null) {
        //     client.create()
        //             .creatingParentsIfNeeded() // 若父节点不存在，则会自动创建父节点
        //             .withMode(CreateMode.PERSISTENT)  // 指定创建持久节点
        //             .forPath(servicePath);  // 指定要创建的节点
        // }

        // 创建临时节点
        String hostPath = servicePath + "/" + serviceAddress;
        if (client.checkExists().forPath(hostPath) == null) {
            String host = client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(hostPath);
            System.out.println(host);
        }

    }
}
