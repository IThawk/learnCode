package com.ithawk.demo.spring.v2.formework.context.support;

/**
 * IOC容器实现的顶层设计
 */
public abstract class AbstractApplicationContext {

    //受保护，只提供给子类重写
    public void refresh() throws Exception {}
}
