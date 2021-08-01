package com.abc.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RouteFilter extends ZuulFilter {
    // 创建一个令牌桶，每秒生成2个令牌
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(2);

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
        // RATE_LIMITER.tryAcquire()：
        //       若可以立即获取到1个令牌，则返回true，否则返回false。不阻塞
        // RATE_LIMITER.tryAcquire(5, 3, TimeUnit.SECONDS)：
        //      若在3秒内可以立即获取到5个令牌，则返回true，否则返回false。不阻塞
        // RATE_LIMITER.acquire()：获取1个令牌，若获取不到，则阻塞直到获取到为止
        // RATE_LIMITER.acquire(5)：获取5个令牌，若获取不到，则阻塞直到获取到为止
        if (!RATE_LIMITER.tryAcquire()) {
            // 指定当前请求未通过zuul过滤
            context.setSendZuulResponse(false);
            // 向客户端响应“请求数量太多”，429
            context.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("通过过滤");
        return null;
    }
}
