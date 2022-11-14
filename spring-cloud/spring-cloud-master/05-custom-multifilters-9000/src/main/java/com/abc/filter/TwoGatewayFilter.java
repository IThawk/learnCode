package com.abc.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class TwoGatewayFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {
        // pre-filter
        log.info("pre-filter-222");
        return chain.filter(exchange).then(
                //post-filter
                Mono.fromRunnable(() -> {
                    log.info("post-filter-222");
                })
        );
    }
}



