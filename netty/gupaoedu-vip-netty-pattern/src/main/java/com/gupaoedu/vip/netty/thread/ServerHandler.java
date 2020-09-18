package com.gupaoedu.vip.netty.thread;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Tom.
 */
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    public static final ChannelHandler INSTANCE = new ServerHandler();


    //channelread0是主线程
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        ByteBuf data = Unpooled.directBuffer();
        //从客户端读一个时间戳
        data.writeBytes(msg);
        //模拟一次业务处理，有可能是数据库操作，也有可能是逻辑处理
        Object result = getResult(data);
        //重新写会给客户端
        ctx.channel().writeAndFlush(result);
    }

    //模拟去数据库拿到一个结果
    protected Object getResult(ByteBuf data) {

        int level = ThreadLocalRandom.current().nextInt(1, 1000);

        //计算出每次响应需要的时间，用来做作为QPS的参考数据

        //90.0% == 1ms   1000 100 > 1ms
        int time;
        if (level <= 900) {
            time = 1;
        //95.0% == 10ms    1000 50 > 10ms
        } else if (level <= 950) {
            time = 10;
        //99.0% == 100ms    1000 10 > 100ms
        } else if (level <= 990) {
            time = 100;
        //99.9% == 1000ms    1000 1 > 1000ms
        } else {
            time = 1000;
        }

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }

        return data;
    }

}
