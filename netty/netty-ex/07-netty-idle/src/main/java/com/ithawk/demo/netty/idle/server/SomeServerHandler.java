package com.ithawk.demo.netty.idle.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SomeServerHandler extends ChannelInboundHandlerAdapter {

    // 所有“规定动作”之外的所有事件都可以通过以下方法触发
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventDes = null;
            switch (event.state()) {
                case READER_IDLE: eventDes = "读空闲超时"; break;
                case WRITER_IDLE: eventDes = "写空闲超时"; break;
                case ALL_IDLE: eventDes = "读和写空闲都超时";
            }
            System.out.println(eventDes);
            // 关闭连接
            // ctx.close();

        } else {    // 其它事件触发
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("xxxxxxxxx");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
