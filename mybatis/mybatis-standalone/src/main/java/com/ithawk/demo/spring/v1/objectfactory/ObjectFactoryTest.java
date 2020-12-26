package com.ithawk.demo.spring.v1.objectfactory;

import com.ithawk.demo.spring.v1.domain.Blog;

/**
 * @Author: qingshan
 * @Date: 2019/3/25 19:41
 * @Description: 咕泡学院，只为更好的你
 */
public class ObjectFactoryTest {
    public static void main(String[] args) {
        GPObjectFactory factory = new GPObjectFactory();
        Blog myBlog = (Blog) factory.create(Blog.class);
        System.out.println(myBlog);
    }
}
