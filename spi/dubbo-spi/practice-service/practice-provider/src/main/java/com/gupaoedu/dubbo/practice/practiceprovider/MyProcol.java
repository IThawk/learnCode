package com.gupaoedu.dubbo.practice.practiceprovider;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.RpcException;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/24-21:16
 * 自定义协议
 */
public class MyProcol implements Protocol {
    @Override
    public int getDefaultPort() {
        return 999;
    }

    //暴露服务（Dubbo-> ；）
    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        return null;
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        return null;
    }

    @Override
    public void destroy() {

    }
}
