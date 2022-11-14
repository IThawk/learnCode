package org.apache.dubbo.demo.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.List;


/**
 * dubbo 的SPI扩展点
 */
public class SpiPayDemo {

    public static void main(String[] args) {

        Order order = ExtensionLoader.getExtensionLoader(Order.class).getDefaultExtension();
        order.way();
        Order order1 = ExtensionLoader.getExtensionLoader(Order.class).getExtension("alipay");

    }
}
