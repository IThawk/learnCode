package com.ithawk.demo.netty.springboot.dubbo.discovery;

public interface ServiceDiscovery {
    String discovery(String serviceName);
}
