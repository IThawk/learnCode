package com.ithawk.demo.dubbo.spi.practice.practiceprovider;

import com.ithawk.mybatis.demo.dubbo.practice.ISayHelloService;
import org.apache.dubbo.config.annotation.Service;


@Service //JDKCompiler、
public class SayHelloServiceImpl implements ISayHelloService {

    @Override
    public String sayHello(String content) {
        return "Hello :"+content;
    }
}
