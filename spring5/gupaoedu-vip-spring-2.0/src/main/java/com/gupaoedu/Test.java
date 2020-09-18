package com.gupaoedu;

import com.gupaoedu.vip.spring.demo.action.MyAction;
import com.gupaoedu.vip.spring.demo.service.impl.QueryService;
import com.gupaoedu.vip.spring.formework.context.GPApplicationContext;

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
