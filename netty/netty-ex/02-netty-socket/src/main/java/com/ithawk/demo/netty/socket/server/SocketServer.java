package com.ithawk.demo.netty.socket.server;

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

// 定义服务端启动类
public class SocketServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)
                    // 指定要创建Channel类型
                     .channel(NioServerSocketChannel.class)
                    //添加处理handler
                     .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 获取channel中的Pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            // StringDecoder：字符串解码器，将Channel中的ByteBuf数据解码为String
                            pipeline.addLast(new StringDecoder());
                            // StringEncoder：字符串编码器，将String编码为将要发送到Channel中的ByteBuf
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new SocketServerHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(28888).sync();
            System.out.println("服务器已启动:28888");
            future.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
