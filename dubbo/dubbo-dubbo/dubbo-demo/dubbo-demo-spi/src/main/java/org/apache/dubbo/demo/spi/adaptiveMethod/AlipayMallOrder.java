package org.apache.dubbo.demo.spi.adaptiveMethod;

import org.apache.dubbo.common.URL;

public class AlipayMallOrder implements MallOrder {
    @Override
    public String way() {
        System.out.println("---  使用支付宝支付  ---");
        return "支付宝支付";
    }

    @Override
    public String pay(URL url) {
        System.out.println("---  pay 使用支付宝支付  ---");
        System.out.println(url.getParameter("say","没有获取到参数"));;
        return "pay 支付宝支付";
    }
}
