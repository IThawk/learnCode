package com.abc.configure;

import org.springframework.stereotype.Controller;

@Controller
public class DepartCodeConfigure {

    // 修改负载均衡策略为：随机策略
    // @Bean
    // public IRule loadBalanceRule() {
    //     return new RoundRobinRule();
    // }

    // 修改负载均衡策略为：自定义策略
    // @Bean
    // public IRule loadBalanceRule() {
    //     List<Integer> excludePorts = new ArrayList<>();
    //     excludePorts.add(8083);
    //     return new CustomRule(excludePorts);
    // }

}
