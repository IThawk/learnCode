package com.ithawk.zookeeper.demo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.io.Closeable;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class LeaderSelectorClientA extends LeaderSelectorListenerAdapter implements Closeable {
    //org.apache.curator.framework.recipes.leader.LeaderLatch
    //    //调用start方法开始抢主
    //    void start()
    //
    //    //调用close方法释放leader权限
    //    void close()
    //
    //    //await方法阻塞线程，尝试获取leader权限，但不一定成功，超时失败
    //    boolean await(long, java.util.concurrent.TimeUnit)
    //
    //    //判断是否拥有leader权限
    //    boolean hasLeadership()

    //org.apache.curator.framework.recipes.leader.LeaderLatchListener
    //    //抢主成功时触发
    //    void isLeader()
    //
    //    //抢主失败时触发
    //    void notLeader()




    //表示当前的进程
    private String name;
    //leader选举的API
    private LeaderSelector leaderSelector;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public LeaderSelectorClientA() {

    }

    public LeaderSelectorClientA(String name) {
        this.name = name;
    }

    public LeaderSelector getLeaderSelector() {
        return leaderSelector;
    }

    public void setLeaderSelector(LeaderSelector leaderSelector) {
        this.leaderSelector = leaderSelector;
    }

    //调用start方法开始抢主
    public void start() {
        //开始竞争leader
        leaderSelector.start();
    }

    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        //如果进入当前的方法，意味着当前的进程获得了锁。获得锁以后，这个方法会被回调
        //这个方法执行结束之后，表示释放leader权限
        System.out.println(name + "->现在是leader了"+ new Date());
//        countDownLatch.await(); //阻塞当前的进程防止leader丢失
        Thread.sleep(10000);

        System.out.println(name + "->现在开始释放leader了"+ new Date());
    }

    //调用close方法释放leader权限
    @Override
    public void close() throws IOException {
        leaderSelector.close();
    }

    private static String CONNECTION_STR="127.0.0.1:12181,127.0.0.1:12182,127.0.0.1:12183";

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(CONNECTION_STR).sessionTimeoutMs(50000000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        curatorFramework.start();
        LeaderSelectorClientA leaderSelectorClient = new LeaderSelectorClientA("ClientA");
        LeaderSelector leaderSelector = new LeaderSelector(curatorFramework, "/leader", leaderSelectorClient);
        leaderSelectorClient.setLeaderSelector(leaderSelector);
        leaderSelectorClient.start(); //开始选举
        Thread.sleep(1000000);
    }
}



