package com.gupaoedu.sentinel.sentinelprovider;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Collections;

@SpringBootApplication
public class SentinelProviderApplication {

    public static void main(String[] args) throws IOException {
        //表示当前的节点是集群客户端
        ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);
        SpringApplication.run(SentinelProviderApplication.class, args);
        System.in.read();
    }
}
