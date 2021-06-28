package org.apache.dubbo.demo.spi.activate;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.List;


/**
 * dubbo 的SPI扩展点
 */
public class SpiUseDemo {

    public static void main(String[] args) {
        //dubbo会默认去查找META-INF的SPI定义
        //静态扩展点
        //自适应扩展点
        //激活扩展点
//        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("myprotocol");
//        System.out.println(protocol.getDefaultPort());
//
//        Compiler protocol=ExtensionLoader.getExtensionLoader(Compiler.class).getAdaptiveExtension();
//        //.AdaptiveCompiler@1a93a7ca
//        URL url = new URL("", "", 0);
//        url = url.addParameter("cache", "cache");//添加这段代码 size未11
//        List<Filter> list = ExtensionLoader.getExtensionLoader(Filter.class).getActivateExtension(url, "cache");
//        System.out.println(list.size());

        Color color = ExtensionLoader.getExtensionLoader(Color.class).getExtension("redColor");
        color.sayMy();
        color.sayHello();
        //激活自适应扩展点  可以参考Filter.class 的实现类CacheFilter
        System.out.println("开始自适应扩展点");
        URL url = new URL("", "", 0);
        url = url.addParameter("blueColor", "blueColor");//添加这段代码 size为1
        url = url.addParameter("redColor", "redColor");//添加这段代码 size为1
        //自适应扩展点的group
        List<Color> list =  ExtensionLoader.getExtensionLoader(Color.class).getActivateExtension(url, "","blueColor");

        System.out.println(list.size());
        for (Color color1 :list){
            System.out.println("自适应扩展点的"+ color1.getClass().getName());
            color1.sayHello();
        }
        List<Color> list1 =  ExtensionLoader.getExtensionLoader(Color.class).getActivateExtension(url, "","");

        System.out.println(list1.size());
        for (Color color1 :list1){
            System.out.println("自适应扩展点的"+ color1.getClass().getName());
            color1.sayHello();
        }

        System.out.println("获取blue");
        Color color2 = ExtensionLoader.getExtensionLoader(Color.class).getExtension("blueColor");
        color2.sayMy();
        color2.sayHello();


        URL url2 = URL.valueOf("http://121:8080?color=redColor");
//        url = url.addParameter("blue", "blue");//添加这段代码 size为1
//        url = url.addParameter("red", "red");//添加这段代码 size为1
        //自适应扩展点的group和key的关系是 或
        List<Color> list2 =  ExtensionLoader.getExtensionLoader(Color.class).getActivateExtension(url2, "color","blueColor");
        System.out.println(list2.size());
    }
}
