package com.abc.server;


import com.abc.registry.RegistryCenter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Data
public class RpcServer {
    // 服务注册表
    private Map<String, Object> registryMap = new HashMap<>();
    private List<String> classCache = new ArrayList<>();

    private RegistryCenter center;
    private String serviceAddress;

    // 添加上无参构造器
    public RpcServer() {
    }

    public RpcServer(RegistryCenter center, String serviceAddress) {
        this.center = center;
        this.serviceAddress = serviceAddress;
    }

    public void publish(String basePackage) throws Exception {
        // 将指定包下的提供者类名写入到classCache中进行缓存
        getProviderClass(basePackage);
        // 将提供者名与实例的对应关系写入到注册表，并完成到zk的注册
        doRegister();
    }

    private void getProviderClass(String basePackage) {
        // 加载指定的包为URL
        URL resource = this.getClass().getClassLoader()
                // com.abc.webapp  => com/abc/webapp
                .getResource(basePackage.replaceAll("\\.", "/"));

        // 若没有指定的资源，则直接结束
        if (resource == null) {
            return;
        }

        // 将URL资源转化为File
        File dir = new File(resource.getFile());
        // 遍历指定包及其子孙包中的所有文件，查找.class文件
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                // 若当前file为目录，则递归
                getProviderClass(basePackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                // 去年.class的扩展名，获取到简单类名
                String fileName = file.getName().replace(".class", "");
                classCache.add(basePackage + "." + fileName);
            }
        }
    }

    private void doRegister() throws Exception {
        // 若指定包下没有任何实现类，则直接结束
        if (classCache.size() == 0) {
            return;
        }

        for (String className : classCache) {
            Class<?> clazz = Class.forName(className);
            // 将实现类名与实现类实例写入到map
            registryMap.put(className, clazz.newInstance());

            Class<?>[] interfaces = clazz.getInterfaces();
            if (interfaces.length == 0) {
                return;
            }
            // 将服务名称与提供者主机地址写入到zk
            center.register(interfaces[0].getName(), serviceAddress);
        }

    }

    // 启动服务器
    public void start() throws Exception {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)
                    // 指定存放已经连接上但还来不及处理的请求的队列的长度
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 为了保证请求长连接不被清除掉
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(new RpcServerHandler(registryMap));
                        }
                    });

            String host = serviceAddress.split(":")[0];
            String port = serviceAddress.split(":")[1];

            ChannelFuture future = bootstrap.bind(host, Integer.valueOf(port)).sync();
            System.out.println("Tomcat已启动");
            future.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

}
