package com.ithawk.mybatis.demo;

import com.ithawk.mybatis.demo.vip.spring.demo.action.MyAction;
import com.ithawk.mybatis.demo.vip.spring.formework.context.GPApplicationContext;

/**
 * Created by Tom on 2019/4/13.
 */
public class Test {

    public static void main(String[] args) {

        GPApplicationContext context = new GPApplicationContext("classpath:application.properties");
        try {
            Object object = context.getBean(MyAction.class);
            System.out.println(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
