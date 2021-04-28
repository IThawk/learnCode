package com.ithawk.learn.springboot.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-04-13 23:22
 */
@Component
public class ApplicationUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBeanByClassName(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    public static Object getBeanByClassName(String tClass) {
        return applicationContext.getBean(tClass);
    }
}
