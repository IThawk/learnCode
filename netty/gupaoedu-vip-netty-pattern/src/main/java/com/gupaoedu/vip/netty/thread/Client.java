package com.gupaoedu.vip.netty.thread;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 * Created by Tom.
 */
public class Client {

    private static final String SERVER_HOST = "127.0.0.1";

    public static void main(String[] args) throws Exception {
        new Client().start(8080);
    }

    public void start(int port) throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new FixedLengthFrameDecoder(Long.BYTES));
                        ch.pipeline().addLast(ClientHandler.INSTANCE);
                    }
        });

        //客户端每秒钟向服务端发起1000次请求
        for (int i = 0; i < 1000; i++) {
            bootstrap.connect(SERVER_HOST, port).get();
        }
    }
}
