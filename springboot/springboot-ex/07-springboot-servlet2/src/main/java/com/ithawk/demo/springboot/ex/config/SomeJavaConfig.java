package com.ithawk.demo.springboot.ex.config;

import com.ithawk.demo.springboot.ex.servlet1.SomeServlet;
import com.ithawk.demo.springboot.ex.filter1.SomeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SomeJavaConfig {

    @Bean
    public ServletRegistrationBean<SomeServlet> getServletBean() {
        return new ServletRegistrationBean<>(new SomeServlet(), "/some");
    }

    @Bean
    public FilterRegistrationBean<SomeFilter> getFilterBean() {
        FilterRegistrationBean<SomeFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SomeFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}
