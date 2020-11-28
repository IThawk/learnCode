package com.ithawk.demo.spring.v1.demo;

import com.ithawk.demo.spring.v1.demo.service.IDemoService;
import com.ithawk.demo.spring.v1.mvcframework.v3.servlet.HawkApplication;

/**
 * 容器化加载,
 */
public class DemoClass {
    public static void main(String[] args) {
        HawkApplication demoDispatcherServlet = new HawkApplication("application.properties");
        IDemoService iDemoService = (IDemoService) demoDispatcherServlet.getIoc().get("demoService");
        System.out.println(iDemoService.get("1313")); ;
    }
}
