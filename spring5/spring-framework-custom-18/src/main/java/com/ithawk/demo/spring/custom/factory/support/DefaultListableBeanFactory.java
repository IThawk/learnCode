package com.ithawk.demo.spring.custom.factory.support;

import com.ithawk.demo.spring.custom.factory.ListableBeanFactory;
import com.ithawk.demo.spring.custom.ioc.BeanDefinition;
import com.ithawk.demo.spring.custom.registry.BeanDefinitionRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通过BeanDefinitionRegistry接口去暴露DefaultListableBeanFactory，其实是符合最少认知原则
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry , ListableBeanFactory {
    private Map<String,BeanDefinition> beanDefinitions = new HashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitions.get(beanName);
    }

    @Override
    public List<BeanDefinition> getBeanDefinitions() {
        List<BeanDefinition> beanDefinitionList = new ArrayList<>();
        for (BeanDefinition bd : beanDefinitions.values()){
            beanDefinitionList.add(bd);
        }
        return beanDefinitionList;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition bd) {
        this.beanDefinitions.put(beanName,bd);
    }

    /**
     *
     * @param type 父类型
     * @param <T>
     * @return
     */
    @Override
    public <T> List<T> getBeansByType(Class type) {
        List<T> results = new ArrayList<>();

        List<BeanDefinition> beanDefinitions = getBeanDefinitions();
        for (BeanDefinition bd : beanDefinitions) {
            // 容器类当前类的类型
            Class<?> clazzType = bd.getClazzType();

            if (type.isAssignableFrom(clazzType)){
                results.add((T) getBean(bd.getBeanName()));
            }
        }
        return results;
    }

    @Override
    public List<String> getBeanNamesByType(Class type) {
        return null;
    }
}
