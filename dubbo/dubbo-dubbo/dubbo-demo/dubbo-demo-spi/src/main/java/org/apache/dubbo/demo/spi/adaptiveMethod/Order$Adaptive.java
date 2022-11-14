package org.apache.dubbo.demo.spi.adaptiveMethod;

import org.apache.dubbo.common.extension.ExtensionLoader;

public class Order$Adaptive implements org.apache.dubbo.demo.spi.adaptiveMethod.Order {
    public java.lang.String way() {
        throw new UnsupportedOperationException("The method public abstract java.lang.String org.apache.dubbo.demo.spi.adaptiveMethod.Order.way() of interface org.apache.dubbo.demo.spi.adaptiveMethod.Order is not adaptive method!");
    }

    public java.lang.String pay(org.apache.dubbo.common.URL arg0) {
        if (arg0 == null) throw new IllegalArgumentException("url == null");
        org.apache.dubbo.common.URL url = arg0;
        String extName = url.getParameter("oo", url.getParameter("okl", "wechat"));
        if (extName == null)
            throw new IllegalStateException("Failed to get extension (org.apache.dubbo.demo.spi.adaptiveMethod.Order) name from url (" + url.toString() + ") use keys([oo, okl])");
        org.apache.dubbo.demo.spi.adaptiveMethod.Order extension = (org.apache.dubbo.demo.spi.adaptiveMethod.Order) ExtensionLoader.getExtensionLoader(org.apache.dubbo.demo.spi.adaptiveMethod.Order.class).getExtension(extName);
        return extension.pay(arg0);
    }
}