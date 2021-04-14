package com.abc.loadbalance;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * 随机负载均衡策略
 */
@Component
public class RandomLoadBalance implements LoadBalance {
    @Override
    public String choose(List<String> invokerPaths) {
        int index = new Random().nextInt(invokerPaths.size());
        return invokerPaths.get(index);
    }
}
