package com.abc.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SomeSocketServerHandler
                       extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " , " + msg);
        ctx.channel().writeAndFlush("from server:" + UUID.randomUUID());
        TimeUnit.MILLISECONDS.sleep(100);

        // Attribute<Object> depart = ctx.channel().attr(AttributeKey.valueOf("depart"));
        // System.out.println("depart ----------- " + depart.get());
        // Attribute<Object> addr = ctx.channel().attr(AttributeKey.valueOf("addr"));
        // System.out.println("addr ----------- " + addr.get());

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
