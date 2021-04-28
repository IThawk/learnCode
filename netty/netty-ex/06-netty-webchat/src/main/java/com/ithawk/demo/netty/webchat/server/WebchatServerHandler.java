package com.ithawk.demo.netty.webchat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class WebchatServerHandler extends ChannelInboundHandlerAdapter {

    // 创建一个ChannelGroup，其是一个线程安全的集合，其中存放着与当前服务器相连接的所有Active状态的Channel
    // GlobalEventExecutor是一个单例、单线程的EventExecutor，是为了保证对当前group中的所有Channel的处理
    // 线程是同一个线程
    private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // 只要有客户端Channel给当前的服务端发送了消息，那么就会触发该方法的执行
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取到向服务器发送消息的channel
        Channel channel = ctx.channel();
        // 这里要实现将消息广播给所有group中的客户端Channel
        // 发送给自己的消息与发送给大家的消息是不一样的
        group.forEach(ch -> {
            if(ch != channel) {
                ch.writeAndFlush(channel.remoteAddress() + "：" + msg + "\n");
            } else {
                channel.writeAndFlush("me：" + msg + "\n");
            }
        });
    }

    // 只要有客户端Channel与服务端连接成功就会执行这个方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 获取到当前与服务器连接成功的channel
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "---上线");
        group.writeAndFlush(channel.remoteAddress() + "---上线\n");
        // 将当前channel添加到group中
        group.add(channel);
    }

    // 只要有客户端Channel断开与服务端的连接就会执行这个方法
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 获取到当前要断开连接的Channel
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "------下线");
        group.writeAndFlush(channel.remoteAddress() + "下线，当前在线人数：" + group.size() + "\n");

        // group中存放的都是Active状态的Channel，一旦某Channel的状态不再是Active，
        // group会自动将其从集合中踢出，所以，下面的语句不用写
        // remove()方法的应用场景是，将一个Active状态的channel移出group时使用
        // group.remove(channel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
