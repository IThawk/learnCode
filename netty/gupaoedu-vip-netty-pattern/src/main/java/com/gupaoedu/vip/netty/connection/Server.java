package com.gupaoedu.vip.netty.connection;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Tom
 */
public final class Server {
    public static final int BEGIN_PORT = 8080;
    public static final int N_PORT = 8100;

    public static void main(String[] args) {
        new Server().start(Server.BEGIN_PORT, Server.N_PORT);
    }

    public void start(int beginPort, int nPort) {
        System.out.println("server starting....");

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);

        bootstrap.childHandler(new ConnectionCountHandler());


        for (int i = 0; i <= (nPort - beginPort); i++) {
            final int port = beginPort + i;

            bootstrap.bind(port).addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    System.out.println("bind success in port: " + port);
                }
            });
        }
        System.out.println("server started!");
    }
}