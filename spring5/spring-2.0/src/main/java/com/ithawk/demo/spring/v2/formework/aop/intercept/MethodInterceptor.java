package com.ithawk.demo.spring.v2.formework.aop.intercept;


public interface MethodInterceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
