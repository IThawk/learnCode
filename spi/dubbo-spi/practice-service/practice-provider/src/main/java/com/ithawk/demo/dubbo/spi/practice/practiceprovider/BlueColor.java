package com.ithawk.demo.dubbo.spi.practice.practiceprovider;

import com.ithawk.mybatis.demo.dubbo.practice.ISayHelloService;
import org.springframework.stereotype.Service;

@Service
public class BlueColor implements Color {


    @Override
    public void sayMy() {
        System.out.println("我是蓝色的呀！你喜欢吗?");
    }

    @Override
    public void sayMyService(ISayHelloService iSayHelloService) {
        System.out.println("我是蓝色的呀！你喜欢吗?");
        System.out.println(iSayHelloService.sayHello("dd"));
        ;
    }
}
