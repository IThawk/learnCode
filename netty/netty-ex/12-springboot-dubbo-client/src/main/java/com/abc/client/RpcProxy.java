package com.abc.client;

import com.abc.discovery.ServiceDiscovery;
import com.abc.dto.Invocation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Client的动态代理
 */
@Component
public class RpcProxy {
    @Autowired
    private ServiceDiscovery serviceDiscovery;

    public <T> T create(Class<?> clazz, String prefix) {
        // 泛型方法
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 若调用的是Object的方法，则直接进行本地调用
                        if (Object.class.equals(method.getDeclaringClass())) {
                            return method.invoke(this, args);
                        }
                        // 远程调用
                        return rpcInvoke(clazz, method, args, prefix);
                    }
                });
    }

    private Object rpcInvoke(Class<?> clazz, Method method, Object[] args, String prefix)
            throws Exception {
        RpcClientHandler handler = new RpcClientHandler();
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        String serviceAddress = null;
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    // Nagle算法，以下属性默认是false，即Nagle算法是开启的
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(handler);
                        }
                    });

            serviceAddress = serviceDiscovery.discovery(clazz.getName());
            // 若zk中不存在该服务，则返回null
            if(serviceAddress == null) {
                return null;
            }
            String host = serviceAddress.split(":")[0];
            String port = serviceAddress.split(":")[1];

            ChannelFuture future = bootstrap.connect(host, Integer.valueOf(port)).sync();

            // 将调用信息实例传递给Server端
            Invocation invocation = new Invocation();
            invocation.setClassName(clazz.getName());
            invocation.setMethodName(method.getName());
            invocation.setParamTypes(method.getParameterTypes());
            invocation.setParamValues(args);
            invocation.setPrefix(prefix);

            future.channel().writeAndFlush(invocation).sync();

            future.channel().closeFuture().sync();
        } finally {
            loopGroup.shutdownGracefully();
        }

        return handler.getResult() + " " + serviceAddress;
    }
}
