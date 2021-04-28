package com.xkcoding.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.xkcoding.dubbo.common.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>
 * Hello服务实现
 * </p>
 *
 * @package: com.xkcoding.dubbo.provider.service
 * @description: Hello服务实现
 * @author: yangkai.shen
 * @date: Created in 2018-12-25 16:58
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
@Component
@Slf4j
public class HelloServiceImpl implements HelloService {
    /**
     * 问好
     *
     * @param name 姓名
     * @return 问好
     */
    @Override
    public String sayHello(String name) {
        log.info("someone is calling me......");
        return "say hello to: " + name;
    }

    /**
     * 问好
     *
     * @param s 姓名
     * @return 问好
     */
    @Override
    public String sayMy(Object s) {
        Class<?> clazz = s.getClass();
        log.info("someone is calling me......");
        Field[] fields = clazz.getFields();
        Method[] methods = clazz.getMethods();
        for (Field field : fields) {
            System.out.println(field.getName());
        }

        if (s instanceof HashMap) {
            HashMap<Object, Object> h = (HashMap) s;
            for (Object key : h.keySet()) {
                System.out.println(h.get(key).toString());
            }
        } else {
            ArrayList<Object> h = (ArrayList) s;
            for (Object key : h) {
                if (key instanceof HashMap) {
                    HashMap<Object, Object> value = (HashMap) key;
                    for (Object key1 : value.keySet()) {
                        System.out.println(value.get(key1).toString());
                    }
                }
            }
        }
        return "say hello to: " + clazz.getName();
    }
}
