package com.ithawk.demo.spring.custom.demo.test.v18;

import com.ithawk.demo.spring.custom.demo.po.User;
import com.ithawk.demo.spring.custom.demo.service.UserService;
import com.ithawk.demo.spring.custom.factory.support.DefaultListableBeanFactory;
import com.ithawk.demo.spring.custom.reader.XmlBeanDefinitionReader;
import com.ithawk.demo.spring.custom.resource.ClasspathResource;
import com.ithawk.demo.spring.custom.resource.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用面向对象思维和配置文件的方式去实现容器化管理Bean
 */
public class TestSpringV1803 {
    private DefaultListableBeanFactory beanFactory;

    @Before
    public void before(){

//        String location = "beans.xml";
        String location = "beans.xml";
        // 获取流对象
        // 策略模式
        Resource resource = new ClasspathResource(location);
//        InputStream inputStream = resource.getResource();

        // 按照spring定义的标签语义去解析Document文档
        beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        beanDefinitionReader.loadBeanDefinitions(resource);
    }

    @Test
    public void test() throws Exception{
        UserService userService = (UserService) beanFactory.getBean("userService");

        Map<String,Object> map = new HashMap<>();
        map.put("username","李四");
        List<User> users = userService.queryUsers(map);
        System.out.println(users);
    }

}
