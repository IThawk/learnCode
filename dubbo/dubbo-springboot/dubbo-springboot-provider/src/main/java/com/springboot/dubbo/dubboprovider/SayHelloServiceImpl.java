package com.springboot.dubbo.dubboprovider;

import com.gupaoedu.dubbo.ISayHelloService;
import org.apache.dubbo.config.annotation.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/21-20:32
 */
@Service(loadbalance = "random",timeout = 50000,cluster = "failsafe")
public class SayHelloServiceImpl implements ISayHelloService {

    @Override
    public String sayHello() {
        //这个地方就会去线程停止1秒
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Come in SayHello()");
        return "Hello Dubbo";
    }
//
//    @Override
//    public String test(Object t) {
//        Class<?> clazz = t.getClass();
//        clazz.getName();
//        Method[] methods = clazz.getMethods();
//        for (Method method : methods) {
//            try {
//                Object o = method.invoke(t);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
}
