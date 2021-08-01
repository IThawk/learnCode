package org.apache.dubbo.demo.spi.adaptiveMethod;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * @Adaptive注解
 *  value：为当前扩展类指定的key，是当前扩展类的一个标识。String[]类型，表示一个扩展类可以有多个指定的key。
 *  * @Adaptive()
 *  *  生成的自适应扩展类的规则：String extName = url.getParameter(接口的小写, defaultSpi);
 *  *  注意：小写的规则就是 如果是多个单词：各个单词转成小写 并且使用'.' 连接 ：
 *  例如： String extName = url.getParameter("mall.order", "wechat");
 * @Adaptive({key1,key2})
 *  生成的自适应扩展类的规则：String extName = url.getParameter($key1, url.getParameter($key2, defaultSpi));
 *
 *  生成代码的位置：
 *      org.apache.dubbo.common.extension.AdaptiveClassCodeGenerator#generate()
 *      注意：这个地方就是把多个
 *      org.apache.dubbo.common.extension.AdaptiveClassCodeGenerator#getMethodAdaptiveValue(org.apache.dubbo.common.extension.Adaptive)
        org.apache.dubbo.common.utils.StringUtils#camelToSplitName(java.lang.String, java.lang.String)
 */
public class AdaptiveMethodOrderTest {

    public static void main(String[] args) {
        ExtensionLoader<Order> loader = ExtensionLoader.getExtensionLoader(Order.class);
        Order defaultExtension = loader.getDefaultExtension();
        System.out.println("defaultExtension: " + defaultExtension.getClass().getName());

        Order order = loader.getAdaptiveExtension();
        //使用默认的调用@SPI("wechat")
        URL url = URL.valueOf("xxx://localhost:8080/ooo/jjj");
        System.out.println(order.pay(url));
        // 对非自适应的方法的调用会抛出异常
        // System.out.println(order.way());

        ExtensionLoader<Order> loader1 = ExtensionLoader.getExtensionLoader(Order.class);
        Order order1 = loader1.getAdaptiveExtension();
        URL url1 = URL.valueOf("xxx://localhost:8080/ooo/jjj?order=alipay");
        System.out.println(order1.pay(url1));
        URL url2 = URL.valueOf("xxx://localhost:8080/ooo/jjj?order=wechat");
        System.out.println(order1.pay(url2));

        URL url3 = URL.valueOf("xxx://localhost:8080/ooo/jjj?say=red&order=alipay");
        System.out.println(order1.pay(url3));



        ExtensionLoader<MallOrder> loader2 = ExtensionLoader.getExtensionLoader(MallOrder.class);
        MallOrder order3 = loader2.getAdaptiveExtension();
        URL url4 = URL.valueOf("xxx://localhost:8080/ooo/jjj?mall.order=alipay");
        System.out.println(order3.pay(url4));
        URL url5 = URL.valueOf("xxx://localhost:8080/ooo/jjj?mall.order=wechat");
        System.out.println(order3.pay(url5));

        URL url6 = URL.valueOf("xxx://localhost:8080/ooo/jjj?say=red&mall.order=alipay");
        System.out.println(order3.pay(url6));
    }


}
