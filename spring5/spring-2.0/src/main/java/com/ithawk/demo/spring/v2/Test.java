package com.ithawk.demo.spring.v2;


import com.ithawk.demo.spring.v2.demo.action.MyAction;
import com.ithawk.demo.spring.v2.formework.context.GPApplicationContext;

/**
 * .
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
