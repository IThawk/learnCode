package com.ithawk.demo.dubbo.spi.practice.practiceprovider;

import com.ithawk.demo.spring.v1.dubbo.practice.ISayHelloService;
import org.apache.dubbo.config.annotation.Service;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/24-13:37
 */
@Service //JDKCompiler、
public class SayHelloServiceImpl implements ISayHelloService {

    @Override
    public String sayHello(String content) {
        return "Hello :"+content;
    }
}
