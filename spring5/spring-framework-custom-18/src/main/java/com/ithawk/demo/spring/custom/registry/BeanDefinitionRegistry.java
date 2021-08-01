package com.ithawk.demo.spring.custom.registry;

import com.ithawk.demo.spring.custom.ioc.BeanDefinition;

import java.util.List;

public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String beanName);

    List<BeanDefinition> getBeanDefinitions();

    void registerBeanDefinition(String beanName,BeanDefinition bd);
}
