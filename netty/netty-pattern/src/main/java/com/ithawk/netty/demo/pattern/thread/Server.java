package com.ithawk.netty.demo.pattern.thread;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 *
 */
public class Server {

    private static final int port = 8000;

    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        final EventLoopGroup businessGroup = new NioEventLoopGroup(1000);

        //设置启动
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_REUSEADDR, true);


        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                //自定义长度的解码，每次发送一个long类型的长度数据
                //一会每次传递一个系统的时间戳
                ch.pipeline().addLast(new FixedLengthFrameDecoder(Long.BYTES));
//                ch.pipeline().addLast(ServerThreadPoolHandler.INSTANCE);
                ch.pipeline().addLast(businessGroup, ServerHandler.INSTANCE);
            }
        });


        ChannelFuture channelFuture = bootstrap.bind(port).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("bind success in port: " + port);
            }
        });
    }

}
