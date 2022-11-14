package com.ithawk.rpc.demo.rpc;

import com.ithawk.rpc.demo.rpc.discovery.IServiceDiscovery;
import com.ithawk.rpc.demo.rpc.discovery.ServiceDiscoveryWithZk;

import java.lang.reflect.Proxy;

public class RpcProxyClient {

    private IServiceDiscovery serviceDiscovery = new ServiceDiscoveryWithZk();


    public <T> T clientProxy(final Class<T> interfaceCls, String version) {

        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class<?>[]{interfaceCls}, new RemoteInvocationHandler(serviceDiscovery, version));
    }
}
