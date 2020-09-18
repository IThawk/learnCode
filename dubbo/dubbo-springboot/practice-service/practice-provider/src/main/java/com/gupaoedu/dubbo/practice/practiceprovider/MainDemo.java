package com.gupaoedu.dubbo.practice.practiceprovider;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.compiler.Compiler;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Protocol;

import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/24-21:20
 */
public class MainDemo {

    public static void main(String[] args) {
        //静态扩展点
        //自适应扩展点
        //激活扩展点
        Protocol protocol=ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("myprotocol");
        System.out.println(protocol.getDefaultPort());

//        Compiler protocol=ExtensionLoader.getExtensionLoader(Compiler.class).getAdaptiveExtension();
        //.AdaptiveCompiler@1a93a7ca
        URL url=new URL("","",0);
        url=url.addParameter("cache","cache");
        List<Filter> list=ExtensionLoader.getExtensionLoader(Filter.class).getActivateExtension(url,"cache");
        System.out.println(list.size());

    }
}
