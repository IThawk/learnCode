package org.apache.dubbo.demo.spi.adaptiveMethod;

import org.apache.dubbo.common.extension.ExtensionLoader;

public class MallOrder$Adaptive implements org.apache.dubbo.demo.spi.adaptiveMethod.MallOrder {
    public java.lang.String way() {
        throw new UnsupportedOperationException("The method public abstract java.lang.String org.apache.dubbo.demo.spi.adaptiveMethod.MallOrder.way() of interface org.apache.dubbo.demo.spi.adaptiveMethod.MallOrder is not adaptive method!");
    }

    public java.lang.String pay(org.apache.dubbo.common.URL arg0) {
        if (arg0 == null) throw new IllegalArgumentException("url == null");
        org.apache.dubbo.common.URL url = arg0;
        String extName = url.getParameter("mall.order", "wechat");
        if (extName == null)
            throw new IllegalStateException("Failed to get extension (org.apache.dubbo.demo.spi.adaptiveMethod.MallOrder) name from url (" + url.toString() + ") use keys([mall.order])");
        org.apache.dubbo.demo.spi.adaptiveMethod.MallOrder extension = (org.apache.dubbo.demo.spi.adaptiveMethod.MallOrder) ExtensionLoader.getExtensionLoader(org.apache.dubbo.demo.spi.adaptiveMethod.MallOrder.class).getExtension(extName);
        return extension.pay(arg0);
    }
}