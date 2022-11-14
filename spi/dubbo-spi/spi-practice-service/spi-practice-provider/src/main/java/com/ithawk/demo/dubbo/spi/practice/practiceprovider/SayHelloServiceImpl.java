package com.ithawk.demo.dubbo.spi.practice.practiceprovider;

import com.ithawk.demo.spring.v1.dubbo.practice.ISayHelloService;
import org.apache.dubbo.config.annotation.Service;


@Service //JDKCompiler、
public class SayHelloServiceImpl implements ISayHelloService {

    @Override
    public String sayHello(String content) {
        return "Hello :"+content;
    }
}
