package com.ithawk.demo.spring.custom.reader;

import org.dom4j.Element;

public interface BeanDefinitionDocumentReader {

    /**
     * 注册bean定义 数据
     * @param rootElement
     */
    void registerBeanDefinitions(Element rootElement);
}
