package com.abc.registry;

/**
 * 注册规范
 */
public interface RegistryCenter {
    /**
     *
     * @param serviceName  注册到注册中心的服务名称，一般为业务接口名
     * @param serviceAddress  提供该服务的主机的ip:port
     */
    void register(String serviceName, String serviceAddress) throws Exception;
}
