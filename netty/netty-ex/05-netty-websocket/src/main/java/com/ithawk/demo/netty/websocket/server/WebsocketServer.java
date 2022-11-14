package com.ithawk.demo.netty.websocket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebsocketServer {
    public static void main(String[] args) throws InterruptedException {
        String s = "d";
        s.contains(s);
        List<String> l = new ArrayList<>();
        l.contains("dd");
        Set<String> set = new HashSet<>();
        set.contains("dd");
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        // 当Channel初始创建完毕后就会触发该方法的执行，用于初始化Channel
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 添加Http编解码器
                            pipeline.addLast(new HttpServerCodec());
                            // 添加大块数据Chunk处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            // 添加Chunk聚合处理器
                            pipeline.addLast(new HttpObjectAggregator(4096));
                            // 添加WebSocket协议转换处理器
                            pipeline.addLast(new WebSocketServerProtocolHandler("/some"));
                            // 添加自定义处理器
                            pipeline.addLast(new WebsocketServerHandler());
                        }
                    });

            ChannelFuture future = bootstrap.bind(8888).sync();
            System.out.println("服务器启动成功。监听的端口号为：8888");
            future.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
