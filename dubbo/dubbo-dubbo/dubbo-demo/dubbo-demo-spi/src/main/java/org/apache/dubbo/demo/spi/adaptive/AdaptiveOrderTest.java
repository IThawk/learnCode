package org.apache.dubbo.demo.spi.adaptive;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;



public class AdaptiveOrderTest {

    public static void main(String[] args) {
        ExtensionLoader<Order> loader = ExtensionLoader.getExtensionLoader(Order.class);

        Order defaultExtension = loader.getDefaultExtension();
        System.out.println("defaultExtension: " + defaultExtension.getClass().getName());

        //使用默认的调用@SPI("wechat")
        Order order = loader.getAdaptiveExtension();
        System.out.println("getAdaptiveExtension :" + order.getClass().getName());
        System.out.println(order.way());


        ExtensionLoader<Order> loader1 = ExtensionLoader.getExtensionLoader(Order.class);
        Order order1 = loader1.getAdaptiveExtension();
        System.out.println("getAdaptiveExtension :" + order.getClass().getName());
        //修改默认使用 alipay 只能加载一个自适应类（加载的顺序就是文件中类的顺序 ）（如果是多个类的时候 这个地方强转 会报错）
        ((AdaptiveOrder) order1).setDefaultName("alipay");
        System.out.println(order1.way());
        System.out.println("-----------------------------------------------------");
        ((AdaptiveOrder) order1).setDefaultName("adaptive1");
        System.out.println(order1.way());
    }

}
