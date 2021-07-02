package com.ithawk.demo.dubbo.spi.practice.practiceprovider;

import com.ithawk.demo.spring.v1.dubbo.practice.ISayHelloService;
import org.apache.dubbo.common.extension.SPI;

@SPI("color")
public interface Color {

    void sayMy();

    void sayMyService(ISayHelloService iSayHelloService);
}
