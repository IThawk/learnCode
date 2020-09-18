package com.gupaoedu.sentinel.server;

import com.alibaba.csp.sentinel.cluster.server.ClusterTokenServer;
import com.alibaba.csp.sentinel.cluster.server.SentinelDefaultTokenServer;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;

import java.util.Collections;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/10-21:23
 */
public class ClusterServer {

    public static void main(String[] args) throws Exception {
        ClusterTokenServer tokenServer=new SentinelDefaultTokenServer();
        ClusterServerConfigManager.loadGlobalTransportConfig(
                new ServerTransportConfig().setIdleSeconds(600).setPort(9999));
        ClusterServerConfigManager.loadServerNamespaceSet(Collections.singleton("App-Mic")); //设置成动态
        tokenServer.start();
    }
}
