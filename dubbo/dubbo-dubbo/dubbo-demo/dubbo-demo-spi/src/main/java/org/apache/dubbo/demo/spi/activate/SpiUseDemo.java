package org.apache.dubbo.demo.spi.activate;

import com.alibaba.fastjson.JSON;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.List;
import java.util.Set;


/**
 * @Activate注解
 * 在@Activate注解中共有五个属性，其中before、after两个属性已经过时，剩余有效属性还有三个。它们的意义为：
 *  group：为扩展类指定所属的组别，是当前扩展类的一个标识。String[]类型，表示一个扩展类可以属于多个组。
 *  value：为当前扩展类指定的key，是当前扩展类的一个标识。String[]类型，表示一个扩展类可以有多个指定的key。
 *  order：指定筛选条件相同的扩展类的加载顺序。序号越小，优先级越高。默认值为0。
 * org.apache.dubbo.common.extension.support.ActivateComparator   这个地方将激活扩展点进行排序
 *    java.util.List#sort(java.util.Comparator)
 */
public class SpiUseDemo {

    public static void main(String[] args) {

        Color color = ExtensionLoader.getExtensionLoader(Color.class).getExtension("redColor");
        System.out.println("------直接加载spi-----");
        color.sayMy();
        color.sayHello();
        Set<String> supportedExtensions = ExtensionLoader.getExtensionLoader(Color.class).getSupportedExtensions();
        System.out.println("supportedExtensions: " +JSON.toJSONString(supportedExtensions));

        //  一定要设置 SPI 的 value 属性 且 默认的 不能是 包装类
        Color defaultExtension = ExtensionLoader.getExtensionLoader(Color.class).getDefaultExtension();
        System.out.println("defaultExtension: " + defaultExtension.getClass().getName());
        //激活自适应扩展点  可以参考Filter.class 的实现类CacheFilter
        System.out.println("----------------开始自适应扩展点---------------------------");
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
