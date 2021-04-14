package com.abc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.ArrayList;
import java.util.List;

// 定义服务端启动类
public class SomeServer {
    private List<ChannelFuture> futures = new ArrayList<>();
    private EventLoopGroup parentGroup = new NioEventLoopGroup();
    private EventLoopGroup childGroup = new NioEventLoopGroup();

    public void start(List<Integer> ports) throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(parentGroup, childGroup)
                 .channel(NioServerSocketChannel.class)
                 .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new SomeServerHandler());
                    }
                });

        for (Integer port : ports) {
            // 生成一个future
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("服务器正在启动中。。。");
            future.addListener(f -> {
                if (f.isSuccess()) {
                    System.out.println("服务器已启动，监听的端口为：" + port);
                }
            });
            // 将所有生成的future添加到集合中
            futures.add(future);
        }
    }

    // 关闭所有Channel
    public void closeAllChannel() {
        System.out.println("所有Channel已经全部关闭");
        for (ChannelFuture future : futures) {
            future.channel().close();
        }
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }
}
