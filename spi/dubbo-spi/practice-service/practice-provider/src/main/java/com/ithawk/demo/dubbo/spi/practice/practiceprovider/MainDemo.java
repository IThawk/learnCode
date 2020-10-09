package com.ithawk.demo.dubbo.spi.practice.practiceprovider;

import com.ithawk.demo.spring.v1.dubbo.practice.ISayHelloService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Protocol;

import java.util.List;

/**
 * dubbo 的SPI扩展点
 */
public class MainDemo {

    public static void main(String[] args) {
        //dubbo会默认去查找META-INF的SPI定义
        //静态扩展点
        //自适应扩展点
        //激活扩展点
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("myprotocol");
        System.out.println(protocol.getDefaultPort());

//        Compiler protocol=ExtensionLoader.getExtensionLoader(Compiler.class).getAdaptiveExtension();
        //.AdaptiveCompiler@1a93a7ca
        URL url = new URL("", "", 0);
        url = url.addParameter("cache", "cache");//添加这段代码 size未11
        List<Filter> list = ExtensionLoader.getExtensionLoader(Filter.class).getActivateExtension(url, "cache");
        System.out.println(list.size());

        Color color = ExtensionLoader.getExtensionLoader(Color.class).getExtension("red");
        ISayHelloService iSayHelloService = new SayHelloServiceImpl();
        color.sayMy();

        color.sayMyService(iSayHelloService);

        Color color2 = ExtensionLoader.getExtensionLoader(Color.class).getExtension("blue");
        color2.sayMy();

        color2.sayMyService(iSayHelloService);
    }
}
