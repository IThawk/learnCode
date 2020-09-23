package com.ithawk.mybatis.demo.vip.spring.formework.beans.support;

import com.ithawk.mybatis.demo.vip.spring.formework.beans.config.GPBeanDefinition;
import com.ithawk.mybatis.demo.vip.spring.formework.context.support.GPAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tom
 */
public class GPDefaultListableBeanFactory extends GPAbstractApplicationContext {

    //存储注册信息的BeanDefinition,伪IOC容器
    protected final Map<String, GPBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, GPBeanDefinition>();
}
