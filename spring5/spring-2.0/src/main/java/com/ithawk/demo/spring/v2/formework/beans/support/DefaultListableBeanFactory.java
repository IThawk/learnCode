package com.ithawk.demo.spring.v2.formework.beans.support;

import com.ithawk.demo.spring.v2.formework.context.support.AbstractApplicationContext;
import com.ithawk.demo.spring.v2.formework.beans.config.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class DefaultListableBeanFactory extends AbstractApplicationContext {

    //存储注册信息的BeanDefinition,伪IOC容器
    protected final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
}
