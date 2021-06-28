package org.apache.dubbo.demo.spi.adaptive;

import org.apache.dubbo.common.extension.SPI;

@SPI("wechat")
public interface Order {
    String way();
}
