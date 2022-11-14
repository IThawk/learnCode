package com.ithawk.demo.springboot.ex.config;

import com.ithawk.demo.springboot.ex.interceptor.SomeIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class SomeWebMvcConfiguration extends WebMvcConfigurationSupport {

    // @Override
    // protected void addInterceptors(InterceptorRegistry registry) {
    //     registry.addInterceptor(new SomeIntercepter())
    //             .addPathPatterns("/first/**");    // 指定拦截包含/first的URL
    // }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SomeIntercepter())
                .excludePathPatterns("/first/**");    // 拦截所有请求，除了指定的URI之外
    }
}
