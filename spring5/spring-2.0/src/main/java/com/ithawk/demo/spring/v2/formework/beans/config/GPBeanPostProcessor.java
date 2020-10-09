package com.ithawk.demo.spring.v2.formework.beans.config;

/**
 * Created by Tom on 2019/4/13.
 */
public class GPBeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }
}
