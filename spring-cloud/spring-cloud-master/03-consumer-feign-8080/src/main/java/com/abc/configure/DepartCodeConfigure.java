package com.abc.configure;

import com.abc.balance.CustomRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DepartCodeConfigure {

    // 修改负载均衡策略为：随机策略
    // @Bean
    // public IRule loadBalanceRule() {
    //     return new RoundRobinRule();
    // }

    // 修改负载均衡策略为：自定义策略
    @Bean
    public IRule loadBalanceRule() {
        List<Integer> excludePorts = new ArrayList<>();
        excludePorts.add(8083);
        return new CustomRule(excludePorts);
    }

}
