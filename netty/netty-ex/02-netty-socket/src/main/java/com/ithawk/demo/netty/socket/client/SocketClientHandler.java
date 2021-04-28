package com.ithawk.demo.netty.socket.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class SocketClientHandler extends SimpleChannelInboundHandler<String> {

    // msg的消息类型与类中的泛型类型是一致的
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //逻辑是接受到服务端的消息，然后会写消息到服务端
        System.out.println("client get msg:"+ctx.channel().remoteAddress() + "，" + msg);
        ctx.channel().writeAndFlush("from client：" + LocalDateTime.now());
        TimeUnit.MILLISECONDS.sleep(500);
    }

    // 当Channel被激活后会触发该方法的执行
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel激活");
        ctx.channel().writeAndFlush("from client：begin talking");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
