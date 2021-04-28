package com.ithawk.demo.netty.webchat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class WebchatClient {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LineBasedFrameDecoder(2048));
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new WebchatClientHandler());
                    }
                });

        ChannelFuture future = bootstrap.connect("localhost", 8888).sync();
        Scanner s = new Scanner(System.in);
        System.out.println("请输入字符串：");
        while (true) {
            String line = s.nextLine();
            if (line.equals("exit")) {
                System.out.println("我需要退出聊天了");
                group.shutdownGracefully();
                break;
            }
            System.out.println("输入>>>" + line);
            // 获取键盘输入
            InputStreamReader is = new InputStreamReader(
                    new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8)), "UTF-8");
            BufferedReader br = new BufferedReader(is);
            // 将输入的内容写入到Channel
            future.channel().writeAndFlush(br.readLine() + "\r\n");
        }
    }
}
