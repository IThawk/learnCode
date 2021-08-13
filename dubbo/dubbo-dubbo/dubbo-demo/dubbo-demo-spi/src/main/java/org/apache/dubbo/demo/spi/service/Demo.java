package org.apache.dubbo.demo.spi.service;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.config.spring.extension.SpringExtensionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ithawk
 * @projectName dubbo
 * @description: TODO
 * @date 2021/8/129:05
 */
public class Demo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("dubbo-spi.xml");
        app.start();
        DemoService memberAction = app.getBean(DemoService.class);
        memberAction.sayHello();
        //添加容器
        SpringExtensionFactory.addApplicationContext(app);
        SpiService order1 = ExtensionLoader.getExtensionLoader(SpiService.class).getExtension("alipay");
        order1.doSomeThing();
    }
}
