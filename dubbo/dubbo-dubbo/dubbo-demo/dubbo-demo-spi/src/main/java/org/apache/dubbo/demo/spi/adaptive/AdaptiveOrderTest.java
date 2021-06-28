package org.apache.dubbo.demo.spi.adaptive;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;


/**
 * @Activate注解
 * 在@Activate注解中共有五个属性，其中before、after两个属性已经过时，剩余有效属性还有三个。它们的意义为：
 *  group：为扩展类指定所属的组别，是当前扩展类的一个标识。String[]类型，表示一个扩展类可以属于多个组。
 *  value：为当前扩展类指定的key，是当前扩展类的一个标识。String[]类型，表示一个扩展类可以有多个指定的key。
 *  order：指定筛选条件相同的扩展类的加载顺序。序号越小，优先级越高。默认值为0。
 */
public class AdaptiveOrderTest {

    public static void main(String[] args) {
        ExtensionLoader<Order> loader = ExtensionLoader.getExtensionLoader(Order.class);
        //使用默认的调用@SPI("wechat")
        Order order = loader.getAdaptiveExtension();
        System.out.println(order.way());


        ExtensionLoader<Order> loader1 = ExtensionLoader.getExtensionLoader(Order.class);
        Order order1 = loader1.getAdaptiveExtension();
        //修改默认使用 alipay
        ((AdaptiveOrder) order1).setDefaultName("alipay");
        System.out.println(order1.way());
    }

}
