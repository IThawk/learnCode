package com.ithawk.mybatis.demo.sentinel.sentinelprovider;

import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/10-21:26
 * 从nacos上去获得动态的限流规则
 */
public class NacosDataSourceInitFunc implements InitFunc {


    private final String CLUSTER_SERVER_HOST="localhost"; //token-server的地址
    private final int CLUSTER_SERVER_PORT=9999;
    private final int REQUEST_TIME_OUT=200000; //请求超时时间

    private final String APP_NAME="App-Mic"; //namespace

    //nacos的配置()
    private final String remoteAddress="192.168.13.106"; //nacos 配置中心的服务host
    private final String groupId="SENTINEL_GROUP";
    private final String FLOW_POSTFIX="-flow-rules"; //dataid（names+postfix）

    //意味着当前的token-server会从nacos上获得限流的规则
    @Override
    public void init() throws Exception {
         //加载集群-信息
        loadClusterClientConfig();

        registryClusterFlowRuleProperty();
    }

    private void loadClusterClientConfig(){
        ClusterClientAssignConfig assignConfig=new ClusterClientAssignConfig();
        assignConfig.setServerHost(CLUSTER_SERVER_HOST);
        assignConfig.setServerPort(CLUSTER_SERVER_PORT);
        ClusterClientConfigManager.applyNewAssignConfig(assignConfig);

        ClusterClientConfig clientConfig=new ClusterClientConfig();
        clientConfig.setRequestTimeout(REQUEST_TIME_OUT);
        ClusterClientConfigManager.applyNewConfig(clientConfig);
    }

    //注册动态数据源
    private void registryClusterFlowRuleProperty(){
        ReadableDataSource<String, List<FlowRule>> rds=
                    new NacosDataSource<List<FlowRule>>(remoteAddress,groupId,APP_NAME+FLOW_POSTFIX,
                            source -> JSON.parseObject(source,new TypeReference<List<FlowRule>>(){}));
        FlowRuleManager.register2Property(rds.getProperty());
    }

}
