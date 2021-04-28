package com.xkcoding.orm.mybatis;

import com.xkcoding.orm.mybatis.util.PropertyUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 * 启动类
 * </p>
 *
 * @package: com.xkcoding.orm.mybatis
 * @description: 启动类
 * @author: yangkai.shen
 * @date: Created in 2018/11/8 10:52
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@SpringBootApplication
public class SpringBootDemoOrmMybatisApplication {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s1 = PropertyUtil.getProperty(0, "TEST_O1");
        String s2 = PropertyUtil.getProperty(1, "TEST_O1");
        String s3 = PropertyUtil.getProperty(2, "TEST_O1");
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        SpringApplication.run(SpringBootDemoOrmMybatisApplication.class, args);
    }
}
