package com.abc.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SomeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 显示客户端的ip与port
        System.out.print(ctx.channel().remoteAddress() + ",");
        // 显示当前Server的ip与port，就是当前监听的端口号
        System.out.println(ctx.channel().localAddress() + "，" + msg);
        // 向客户端发送数据
        ctx.channel().writeAndFlush("from server：" + UUID.randomUUID());
        TimeUnit.MILLISECONDS.sleep(500);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
