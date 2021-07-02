package org.apache.dubbo.demo.spi.wrapper;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI("wechat")
public interface Order {

    String way();

    @Adaptive
    String pay(URL url);
}
