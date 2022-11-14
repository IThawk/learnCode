package com.ithawk.demo.netty.stickybag.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class StickybagClientHandler extends ChannelInboundHandlerAdapter {

    private String message = "Hello World";


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("首次获取服务端消息");
        byte[] bytes = message.getBytes();
        ByteBuf buffer = null;
        for(int i=0; i<100; i++) {
            // 申请缓存空间
            buffer = Unpooled.buffer(bytes.length);
            // 将数据写入到缓存
            buffer.writeBytes(bytes);
            // 将缓存中的数据写入到Channel
            ctx.writeAndFlush(buffer);
        }

//         for(int i=0; i<100; i++) {
//             System.out.println("发送。。。");
//             ctx.channel().writeAndFlush(message);
//         }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
