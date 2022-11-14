package com.ithawk.demo.dubbo.spi.practice.practiceprovider;

import com.ithawk.demo.spring.v1.dubbo.practice.ISayHelloService;
import org.apache.dubbo.common.extension.Activate;
import org.springframework.stereotype.Service;

import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;
import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;
import static org.apache.dubbo.common.constants.FilterConstants.CACHE_KEY;


@Activate(group = {CONSUMER, PROVIDER}, value = "activate")
public class ActivateColor implements Color {


    @Override
    public void sayMy() {
        System.out.println("我是activate的呀！你喜欢吗?");
    }

    @Override
    public void sayMyService(ISayHelloService iSayHelloService) {
        System.out.println("我是activate的呀！你喜欢吗?");
        System.out.println(iSayHelloService.sayHello("activate"));
        ;
    }
}
