package com.ithawk.rpc.demo.vip;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */

@Configuration
@ComponentScan(basePackages = "com.ithawk.rpc")
public class SpringConfig {

    @Bean(name="gpRpcServer")
    public RpcServer gpRpcServer(){
        return new RpcServer(8989);
    }
}
