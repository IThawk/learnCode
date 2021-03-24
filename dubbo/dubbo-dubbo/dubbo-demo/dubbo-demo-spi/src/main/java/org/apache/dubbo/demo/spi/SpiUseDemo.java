package org.apache.dubbo.demo.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Filter;

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

        Color color = ExtensionLoader.getExtensionLoader(Color.class).getExtension("red");
        color.sayMy();
        //激活自适应扩展点  可以参考Filter.class 的实现类CacheFilter
        System.out.println("开始自适应扩展点");
        URL url = new URL("", "", 0);
        url = url.addParameter("blue", "blue");//添加这段代码 size未11
        url = url.addParameter("red", "red");//添加这段代码 size未11
        //自适应扩展点的group
        List<Color> list =  ExtensionLoader.getExtensionLoader(Color.class).getActivateExtension(url, "","blue");
        System.out.println(list.size());
        for (Color color1 :list){
            color1.sayHello();
        }

        System.out.println("获取blue");
        Color color2 = ExtensionLoader.getExtensionLoader(Color.class).getExtension("blue");
        color2.sayMy();
        color2.sayHello();
    }
}
