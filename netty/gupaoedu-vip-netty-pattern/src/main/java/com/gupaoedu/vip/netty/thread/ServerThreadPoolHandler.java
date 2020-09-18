package com.gupaoedu.vip.netty.thread;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Tom.
 */
@ChannelHandler.Sharable
public class ServerThreadPoolHandler extends ServerHandler {
    public static final ChannelHandler INSTANCE = new ServerThreadPoolHandler();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(1000);


    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, ByteBuf msg) {
        final ByteBuf data = Unpooled.directBuffer();
        data.writeBytes(msg);
        threadPool.submit(new Runnable() {
            public void run() {
                Object result = getResult(data);
                ctx.channel().writeAndFlush(result);
            }
        });

    }
}
