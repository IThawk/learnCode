package com.gupaoedu.vip.registry;

public interface IRegistryCenter {

    /**
     * 服务注册名称和服务注册地址实现服务的管理
     * @param serviceName
     * @param serviceAddress
     */
    void registry(String serviceName,String serviceAddress);
}
