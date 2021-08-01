package com.ithawk.demo.springcloud.gateway.security;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    private Logger logger = LoggerFactory.getLogger(AuthenticationGatewayFilterFactory.class);
    @Override
    public GatewayFilter apply(Object config) {
       
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
                List<String> accountInfo = exchange.getResponse().getHeaders().get("AccountInfo");
                exchange.getResponse().getHeaders().remove("AccountInfo");//移除包头中的用户信息不需要返回给客户端
                if(accountInfo != null && accountInfo.size() > 0) {
                    String gmAccountInfoJson = accountInfo.get(0);//获取header中的用户信息
                    //将信息放在session中，在后面认证的请求中使用
                    exchange.getSession().block().getAttributes().put("AccountInfo", gmAccountInfoJson);
                }
                logger.debug("登陆返回信息:{}",accountInfo);
        }));
    }
}
