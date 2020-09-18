package com.gupaoedu.pub2018.chapter17;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class WatcherDemo {

    private static String CONNECTION_STR="192.168.13.102:2181,192.168.13.103:2181,192.168.13.104:2181";

    public static void main(String[] args) throws Exception {
        //PathChildCache  --针对于子节点的创建、删除和更新 触发事件
        //NodeCache  针对当前节点的变化触发事件
        //TreeCache  综合事件

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(CONNECTION_STR).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        curatorFramework.start();
        addListenerWithChild(curatorFramework);

        System.in.read();

    }

    //配置中心
    //创建、修改、删除
    private static void addListenerWithNode(CuratorFramework curatorFramework) throws Exception {
        NodeCache nodeCache=new NodeCache(curatorFramework,"/watch",false);
        NodeCacheListener nodeCacheListener= () -> {
            System.out.println("receive Node Changed");
            System.out.println(nodeCache.getCurrentData().getPath()+"---"+new String(nodeCache.getCurrentData().getData()));
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }

    //实现服务注册中心的时候，可以针对服务做动态感知
    private static void addListenerWithChild(CuratorFramework curatorFramework) throws Exception {
        PathChildrenCache nodeCache=new PathChildrenCache(curatorFramework,"/watch",true);
        PathChildrenCacheListener nodeCacheListener= (curatorFramework1,pathChildrenCacheEvent) -> {
            System.out.println(pathChildrenCacheEvent.getType()+"->"+new String(pathChildrenCacheEvent.getData().getData()));
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start(PathChildrenCache.StartMode.NORMAL);
    }
}
