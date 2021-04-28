package com.ithawk.demo.netty.fixedLeng.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class FixedLengthFrameDecoderServerHandler extends SimpleChannelInboundHandler<String> {

    private int counter;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("Server端接收到的第【" + ++counter + "】个数据包：" + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
