package com.ithawk.demo.zookeeper.springboot.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author ithawk
 * @projectName zookeeper
 * @description: zookeeper的工具类
 * @date 2021/12/3114:08
 */
@Component
@Slf4j
public class ZookeeperUtils {

    @Autowired
    private CuratorFramework curatorFramework;


    /**
     * 创建节点
     * CreateMode.PERSISTENT ：持久化节点，当客户端断开连接时，不会被删除
     * CreateMode.PERSISTENT_SEQUENTIAL：持久化递增节点，当客户端断开连接时，znode不会被自动删除，它的名字会以一个单调递增的数字追加。
     * CreateMode.EPHEMERAL：临时节点，当客户端断开连接时，会被删除
     * CreateMode.EPHEMERAL_SEQUENTIAL：递增临时节点，当客户端断开连接时，znode将被删除，它的名字将以一个单调递增的数字追加。
     *
     * @param path
     * @throws Exception
     */
    public void createData(String path, String value, CreateMode createMode) throws Exception {
        curatorFramework.create().creatingParentsIfNeeded().withMode(createMode).
                forPath(path, value.getBytes());
        System.out.println("OK");

    }

    /**
     * 更新节点数据
     *
     * @param path
     * @throws Exception
     */
    public void updateData(String path, String value) throws Exception {
        curatorFramework.setData().forPath(path, value.getBytes());

    }

    /**
     * @param path
     * @description: 删除节点
     * @return: void
     * @author IThawk
     * @date: 2021/12/31 15:25
     */
    public void deleteData(String path) throws Exception {
        Stat stat = new Stat();
        String value = new String(curatorFramework.getData().storingStatIn(stat).forPath(path));
        curatorFramework.delete().withVersion(stat.getVersion()).forPath(path);
    }

    //    //配置中心
//    //创建、修改、删除
    private void addListenerWithNode() throws Exception {
        //节点添加监听操作
        NodeCache nodeCache = new NodeCache(curatorFramework, "/watch", false);
        CuratorCache curatorCache = CuratorCache.builder(curatorFramework, "/watch").build();
        NodeCacheListener nodeCacheListener = () -> {
            System.out.println("receive Node Changed");
            System.out.println(nodeCache.getCurrentData().getPath() + "---"
                    + new String(nodeCache.getCurrentData().getData()));
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }

    public void addListenerWithNode(String path) throws Exception {
        CuratorCache curatorCache = CuratorCache.builder(curatorFramework, path).build();
        CuratorCacheListener listener = CuratorCacheListener.builder()
                .forCreates(node -> log.info(String.format("Node created: [%s]", node)))
                .forChanges((oldNode, node) -> log.info(String.format("Node changed. Old: [%s] New: [%s]", oldNode, node)))
                .forDeletes(oldNode -> log.info(String.format("Node deleted. Old value: [%s]", oldNode)))
                .forInitialized(() -> log.info("Cache initialized"))
                .build();

        curatorCache.listenable().addListener(listener);
        curatorCache.start();
    }

    public void addListenerWithChild(String path) throws Exception {
        PathChildrenCacheListener listener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED: {
                        log.info("Node added: " + ZKPaths.getNodeFromPath(event.getData().getPath()) + " data:" + new String(event.getData().getData()).toString());
                        break;
                    }

                    case CHILD_UPDATED: {
                        log.info("Node changed: " + ZKPaths.getNodeFromPath(event.getData().getPath()) + " data:" + new String(event.getData().getData()));
                        break;
                    }

                    case CHILD_REMOVED: {
                        log.info("Node removed: " + ZKPaths.getNodeFromPath(event.getData().getPath()) + " data:" + new String(event.getData().getData()));
                        break;
                    }
                }
            }
        };
        CuratorCacheListener curatorCacheListener = CuratorCacheListener.builder()
                .forPathChildrenCache(path, curatorFramework, listener)
                .forInitialized(() -> log.info("ChildCache initialized"))
                .build();
        CuratorCache curatorCache = CuratorCache.builder(curatorFramework, path).build();
        curatorCache.listenable().addListener(curatorCacheListener);
        curatorCache.start();

    }


    //实现服务注册中心的时候，可以针对服务做动态感知 子节点通知事件
    private static void addListenerWithChild(CuratorFramework curatorFramework) throws Exception {
        PathChildrenCache nodeCache = new PathChildrenCache(curatorFramework, "/watch", true);
        PathChildrenCacheListener nodeCacheListener = (curatorFramework1, pathChildrenCacheEvent) -> {
            System.out.println(pathChildrenCacheEvent.getType() + "->"
                    + new String(pathChildrenCacheEvent.getData().getData()));
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start(PathChildrenCache.StartMode.NORMAL);
    }
}
