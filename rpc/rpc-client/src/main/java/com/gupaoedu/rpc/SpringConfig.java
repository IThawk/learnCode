package com.gupaoedu.rpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Configuration
public class SpringConfig {

    @Bean(name="rpcPRoxyClient")
    public RpcProxyClient proxyClient(){
        return new RpcProxyClient();
    }
}
