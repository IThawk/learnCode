package com.ithawk.springboot.demo.orm.mybatis.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 实现 ApplicationContextAware 可以获取 applicationContext
 */
@Component
public class ApplicationUtils implements ApplicationContextAware {

<<<<<<< HEAD
    private ApplicationContext applicationContext;
=======
    private static ApplicationContext applicationContext;
>>>>>>> aaec40860904e3b1ec6164df914652bc11c958bb

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

<<<<<<< HEAD
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
=======
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }
>>>>>>> aaec40860904e3b1ec6164df914652bc11c958bb
}
