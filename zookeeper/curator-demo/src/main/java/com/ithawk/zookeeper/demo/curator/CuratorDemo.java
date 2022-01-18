package com.ithawk.zookeeper.demo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 *
 */
public class CuratorDemo {

    //    private static String CONNECTION_STR="127.0.0.1:12181";
    private static String CONNECTION_STR = "127.0.0.1:2182";


    public static void main(String[] args) throws Exception {
//        CuratorFramework curatorFramework= CuratorFrameworkFactory.newClient("")

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(CONNECTION_STR)
                .sessionTimeoutMs(5000)
                //重试策略
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        //ExponentialBackoffRetry
        //RetryOneTime  仅仅只重试一次
        //RetryUntilElapsed
        //RetryNTimes

        //启动
        curatorFramework.start();
//        deleteData(curatorFramework);
        createData(curatorFramework);
        updateData(curatorFramework);

        //CRUD
//        curatorFramework.create();
        curatorFramework.getData(); //查询
        curatorFramework.setData(); //修改
//        curatorFramework.delete();// 删除

    }

    /**
     * 创建节点
     * CreateMode.PERSISTENT ：持久化节点，当客户端断开连接时，不会被删除
     * CreateMode.PERSISTENT_SEQUENTIAL：持久化递增节点，当客户端断开连接时，znode不会被自动删除，它的名字会以一个单调递增的数字追加。
     * CreateMode.EPHEMERAL：临时节点，当客户端断开连接时，会被删除
     * CreateMode.EPHEMERAL_SEQUENTIAL：递增临时节点，当客户端断开连接时，znode将被删除，它的名字将以一个单调递增的数字追加。
     *
     * @param curatorFramework
     * @throws Exception
     */
    private static void createData(CuratorFramework curatorFramework) throws Exception {
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).
                forPath("/data/program", "test".getBytes());
        System.out.println("OK");

    }

    /**
     * 更新节点数据
     *
     * @param curatorFramework
     * @throws Exception
     */
    private static void updateData(CuratorFramework curatorFramework) throws Exception {
        curatorFramework.setData().forPath("/data/program", "up".getBytes());

    }

    private static void deleteData(CuratorFramework curatorFramework) throws Exception {
        Stat stat = new Stat();
        String value = new String(curatorFramework.getData().storingStatIn(stat).forPath("/data/program"));
        curatorFramework.delete().withVersion(stat.getVersion()).forPath("/data/program");
    }


}
