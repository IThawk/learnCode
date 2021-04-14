package com.abc.server;

import com.abc.dto.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;

import java.util.Map;

// SimpleChannelInboundHandler：channelRead()方法msg参数会被自动释放
// ChannelInboundHandlerAdapter：channelRead()方法msg参数不会被自动释放
public class RpcServerHandler extends SimpleChannelInboundHandler<Invocation> {
    private Map<String, Object> registryMap;

    public RpcServerHandler(Map<String, Object> registryMap) {
        this.registryMap = registryMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Invocation msg) throws Exception {
        Object result = "没有指定的提供者";

        // 获取要访问的业务接口名
        String interfaceName = msg.getClassName();
        // 获取接口的简单类名
        String simpleInterfaceName = interfaceName.substring(interfaceName.lastIndexOf(".") + 1);
        // 获取接口所在的包名
        String basePackage = interfaceName.substring(0, interfaceName.lastIndexOf("."));
        // 获取用户要访问的提供者实现类的前辍
        String prefix = msg.getPrefix();
        // 构建客户端要访问的提供者的key
        String key = basePackage + "." + prefix + simpleInterfaceName;

        // 若没有指定前辍，则从registryMap中查找第一个指定接口的实现类
        if (StringUtil.isNullOrEmpty(prefix)) {
            // 查找第一个以接口名结尾的实现类名
            for (String registryKey : registryMap.keySet()) {
                if (registryKey.endsWith(simpleInterfaceName)) {
                    key = registryKey;
                    break;
                }
            }
        }

        // 判断注册表中是否存在指定名称(接口名)的服务
        if (registryMap.containsKey(key)) {
            // 从注册表中获取指定的提供者
            Object provider = registryMap.get(key);
            // 按照客户端要求进行本地方法调用
            result = provider.getClass()
                    .getMethod(msg.getMethodName(), msg.getParamTypes())
                    .invoke(provider, msg.getParamValues());
        }

        ctx.writeAndFlush(result);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
