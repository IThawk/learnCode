package org.apache.dubbo.demo.spi.wrapper;

import org.apache.dubbo.common.extension.ExtensionLoader;

public class Order$AdaptiveR implements org.apache.dubbo.demo.spi.wrapper.Order {
    public java.lang.String way() {
        throw new UnsupportedOperationException("The method public abstract java.lang.String org.apache.dubbo.demo.spi.wrapper.Order.way() of interface org.apache.dubbo.demo.spi.wrapper.Order is not adaptive method!");
    }

    public java.lang.String pay(org.apache.dubbo.common.URL arg0) {
        if (arg0 == null) throw new IllegalArgumentException("url == null");
        org.apache.dubbo.common.URL url = arg0;
        String extName = url.getParameter("order", "wechat");
        if (extName == null)
            throw new IllegalStateException("Failed to get extension (org.apache.dubbo.demo.spi.wrapper.Order) name from url (" + url.toString() + ") use keys([order])");
        org.apache.dubbo.demo.spi.wrapper.Order extension =
                (org.apache.dubbo.demo.spi.wrapper.Order)
                        ExtensionLoader.getExtensionLoader(org.apache.dubbo.demo.spi.wrapper.Order.class)
                                .getExtension(extName);
        return extension.pay(arg0);
    }
}