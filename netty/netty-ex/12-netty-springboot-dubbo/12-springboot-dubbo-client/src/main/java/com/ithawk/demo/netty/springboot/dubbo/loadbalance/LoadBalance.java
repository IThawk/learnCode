package com.ithawk.demo.netty.springboot.dubbo.loadbalance;


import java.util.List;

public interface LoadBalance {
    /**
     *
     * @param invokerPaths  所有提供者主机列表
     * @return  负载均衡后的结果
     */
    String choose(List<String> invokerPaths);
}
