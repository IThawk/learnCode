package com.ithawk.demo.springcloud.gateway.security;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.web.server.WebFilter;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.UnpooledByteBufAllocator;
import reactor.core.publisher.Flux;

@EnableWebFluxSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        RedirectServerAuthenticationEntryPoint loginPoint = new RedirectServerAuthenticationEntryPoint("/xinyue-server-a/account/index");
        http.authorizeExchange().pathMatchers("/login").permitAll()
        .and().formLogin().loginPage("/login").authenticationEntryPoint(loginPoint)
        .authenticationSuccessHandler((webFilterExchange,authentication)->{//认证成功之后返回给客户端的信息
            JSONObject result = new JSONObject();
            result.put("code", 0);
            return webFilterExchange.getExchange().getResponse().writeWith(Flux.create(sink -> {
                NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
                try {
                    DataBuffer dataBuffer= nettyDataBufferFactory.wrap(result.toJSONString().getBytes("utf8"));
                    sink.next(dataBuffer);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sink.complete();
            }));
        })
        .and().authorizeExchange().pathMatchers("/xinyue-server-a/account/main").access(new XinyueReactiveAuthorizationManager("Manager", "Dev"))
        .and().authorizeExchange().anyExchange().authenticated()
        .and().csrf().disable();
        SecurityWebFilterChain chain = http.build();
        Iterator<WebFilter>  weIterable = chain.getWebFilters().toIterable().iterator();
        while(weIterable.hasNext()) {
            WebFilter f = weIterable.next();
            if(f instanceof AuthenticationWebFilter) {
              AuthenticationWebFilter webFilter = (AuthenticationWebFilter) f;
              //将自定义的AuthenticationConverter添加到过滤器中
              webFilter.setServerAuthenticationConverter(new XinyueAuthenticationConverter());      
            }
        }
        return chain;
    }
    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        return new ReactiveAuthenticationManagerAdapter((authentication)->{
            if(authentication instanceof XinyueAccountAuthentication) {
                XinyueAccountAuthentication gmAccountAuthentication = (XinyueAccountAuthentication) authentication;
                if(gmAccountAuthentication.getPrincipal() != null) {
                    authentication.setAuthenticated(true);
                    return authentication;
                } else {
                    return authentication;
                }
            } else {
                return authentication;
            }
        });
    }
}
