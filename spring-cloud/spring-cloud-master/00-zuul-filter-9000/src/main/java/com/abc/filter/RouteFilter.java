package com.abc.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class RouteFilter extends ZuulFilter {
    @Override
    public String filterType() {
        // "pre",进行路由之前过滤
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -5;
    }

    @Override
    public boolean shouldFilter() {
        // 获取当前的请求上下文对象
        RequestContext context = RequestContext.getCurrentContext();
        // 从请求上下文中获取当前请求信息
        HttpServletRequest request = context.getRequest();
        String user = request.getParameter("user");
        String uri = request.getRequestURI();
        if (uri.contains("/abc8080") && StringUtils.isEmpty(user)) {
            // 指定当前请求未通过zuul过滤，默认值为true
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("通过过滤");
        return null;
    }
}
