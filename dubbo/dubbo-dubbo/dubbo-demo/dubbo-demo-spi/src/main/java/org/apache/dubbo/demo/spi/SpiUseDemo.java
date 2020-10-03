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
//        URL url = new URL("", "", 0);
//        url = url.addParameter("cache", "cache");//添加这段代码 size未11
//        Color color1 = (Color) ExtensionLoader.getExtensionLoader(Color.class).getActivateExtension(url, "cache");

        Color color2 = ExtensionLoader.getExtensionLoader(Color.class).getExtension("blue");
        color2.sayMy();
    }
}
