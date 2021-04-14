package com.abc.tomcat;

import com.abc.servnet.Servnet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tomcat功能的实现
 */
public class TomcatServer {
    // key为servnet的简单类名，value为对应servnet实例
    private Map<String, Servnet> nameToServnetMap = new ConcurrentHashMap<>();
    // key为servnet的简单类名，value为对应servnet类的全限定性类名
    private Map<String, String> nameToClassNameMap = new HashMap<>();

    private String basePackage;

    public TomcatServer(String basePackage) {
        this.basePackage = basePackage;
    }

    // 启动tomcat
    public void start() throws Exception {
        // 加载指定包中的所有Servnet的类名
        cacheClassName(basePackage);
        // 启动server服务
        runServer();
    }

    private void cacheClassName(String basePackage) {
        // 获取指定包中的资源
        URL resource = this.getClass().getClassLoader()
                // com.abc.webapp  =>  com/abc/webapp
                .getResource(basePackage.replaceAll("\\.", "/"));
        // 若目录中没有任何资源，则直接结束
        if (resource == null) {
            return;
        }

        // 将URL资源转换为File资源
        File dir = new File(resource.getFile());
        // 遍历指定包及其子孙包中的所有文件，查找所有.class文件
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                // 若当前遍历的file为目录，则递归调用当前方法
                cacheClassName(basePackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                String simpleClassName = file.getName().replace(".class", "").trim();
                // key为简单类名，value为全限定性类名
                nameToClassNameMap.put(simpleClassName.toLowerCase(), basePackage + "." + simpleClassName);
            }
        }
        // System.out.println(nameToClassNameMap);
    }

    private void runServer() throws Exception {
        EventLoopGroup parent = new NioEventLoopGroup();
        EventLoopGroup child = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parent, child)
                    // 指定存放请求的队列的长度
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 指定是否启用心跳机制来检测长连接的存活性，即客户端的存活性
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new TomcatHandler(nameToServnetMap, nameToClassNameMap));
                        }
                    });

            ChannelFuture future = bootstrap.bind(8888).sync();
            System.out.println("Tomcat启动成功：监听端口号为8888");
            future.channel().closeFuture().sync();
        } finally {
            parent.shutdownGracefully();
            child.shutdownGracefully();
        }
    }


}
