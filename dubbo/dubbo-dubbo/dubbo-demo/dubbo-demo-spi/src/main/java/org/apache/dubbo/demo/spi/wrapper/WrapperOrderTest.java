package org.apache.dubbo.demo.spi.wrapper;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.List;


public class WrapperOrderTest {

    public static void main(String[] args) {
        ExtensionLoader<Order> loader = ExtensionLoader.getExtensionLoader(Order.class);
        Order order = loader.getAdaptiveExtension();
        System.out.println("------------------WrapperOrderTest------------------");
        //使用默认的调用@SPI("wechat")
        URL url = URL.valueOf("xxx://localhost:8080/ooo/jjj");
        System.out.println(order.pay(url));
        // 对非自适应的方法的调用会抛出异常
        // System.out.println(order.way());

        ExtensionLoader<Order> loader1 = ExtensionLoader.getExtensionLoader(Order.class);
        Order order1 = loader1.getAdaptiveExtension();
        URL url1 = URL.valueOf("xxx://localhost:8080/ooo/jjj?order=alipay");
        System.out.println(order1.pay(url1));
        System.out.println("-------------------WrapperOrderTest-----------------");
        URL url2 = URL.valueOf("xxx://localhost:8080/ooo/jjj?order=wechat");
        System.out.println(order1.pay(url2));
        System.out.println("------------------WrapperOrderTest------------------");
        URL url3 = URL.valueOf("xxx://localhost:8080/ooo/jjj?say=red&order=alipay");
        System.out.println(order1.pay(url3));

        System.out.println("------------------WrapperOrderTest active------------------");
        ExtensionLoader<Order> loader2 = ExtensionLoader.getExtensionLoader(Order.class);
        URL url4 = new URL("", "", 0);
        url4 = url4.addParameter("wrapper", "wrapper");//添加这段代码 size为1

        List<Order> order2 = loader2.getActivateExtension(url4,"","");
        System.out.println(order2.size());
        URL url5 = URL.valueOf("xxx://localhost:8080/ooo/jjj?order=alipay");
        System.out.println(order1.pay(url5));
        System.out.println("-------------------WrapperOrderTest-----------------");

    }

}
