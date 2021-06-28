package org.apache.dubbo.demo.spi.other;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.demo.spi.activate.Color;

import java.util.List;


/**
 * dubbo 的SPI扩展点
 */
public class SpiUseDemo {

    public static void main(String[] args) {


        URL url2 = URL.valueOf("http://121:8080?my.color=redColor");
        url2 = url2.addParameter("blueColor", "blueColor");//添加这段代码 size为1

        //自适应扩展点的group和key的关系是 或
        List<MyColor> list2 =  ExtensionLoader.getExtensionLoader(MyColor.class).getActivateExtension(url2, "","");
        System.out.println(list2.size());

        url2 = url2.addParameter("redColor", "redColor");//添加这段代码 size为1
        //自适应扩展点的group和key的关系是 或
        List<MyColor> list3 =  ExtensionLoader.getExtensionLoader(MyColor.class).getActivateExtension(url2, "","");
        System.out.println(list3.size());

        MyColor color =  ExtensionLoader.getExtensionLoader(MyColor.class).getExtension("blueColor");
        System.out.println(color.color());;
    }
}
