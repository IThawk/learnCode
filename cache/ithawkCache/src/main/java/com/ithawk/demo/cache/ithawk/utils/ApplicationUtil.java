package com.ithawk.demo.cache.ithawk.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ithawk
 * @projectName cache
 * @description: TODO
 * @date 2021/8/911:47
 */
@Component
public class ApplicationUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static Object getBean(Class<?> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static Object invokeMethod(Class<?> clazz, String methodName, Object[] args) {
        Object s = applicationContext.getBean(clazz);
        try {
            try {
                Class clas =s.getClass();
                Method method = clas.getDeclaredMethod(methodName);
                return method.invoke(s,args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
