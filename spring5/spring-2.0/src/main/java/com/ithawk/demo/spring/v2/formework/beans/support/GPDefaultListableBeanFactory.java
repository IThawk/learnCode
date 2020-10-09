package com.ithawk.demo.spring.v2.formework.beans.support;

import com.ithawk.demo.spring.v2.formework.context.support.GPAbstractApplicationContext;
import com.ithawk.demo.spring.v2.formework.beans.config.GPBeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tom
 */
public class GPDefaultListableBeanFactory extends GPAbstractApplicationContext {

    //存储注册信息的BeanDefinition,伪IOC容器
    protected final Map<String, GPBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, GPBeanDefinition>();
}
