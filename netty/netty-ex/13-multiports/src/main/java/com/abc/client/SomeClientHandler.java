package com.abc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class SomeClientHandler extends SimpleChannelInboundHandler<String> {

    // msg的消息类型与类中的泛型类型是一致的
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "，" + msg);
        ctx.channel().writeAndFlush("from client：" + LocalDateTime.now());
        TimeUnit.MILLISECONDS.sleep(500);
    }

    // 当Channel被激活后会触发该方法的执行
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush("from client：begin talking");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
