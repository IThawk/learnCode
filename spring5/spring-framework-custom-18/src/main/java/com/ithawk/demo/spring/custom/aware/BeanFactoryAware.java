package com.ithawk.demo.spring.custom.aware;

import com.ithawk.demo.spring.custom.factory.BeanFactory;

public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory);
}
