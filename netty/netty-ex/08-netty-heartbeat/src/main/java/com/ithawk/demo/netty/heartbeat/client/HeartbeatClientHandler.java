package com.ithawk.demo.netty.heartbeat.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HeartbeatClientHandler extends ChannelInboundHandlerAdapter {
    //
    private ScheduledFuture schedule;
    private GenericFutureListener listener;

    /**
     * 客户端连接时会马上执行这个方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 发送心跳
        sendHeartbeat(ctx.channel());
    }

    private void sendHeartbeat(Channel channel) {
        // 生成一个[1，8)的随机数作为心跳发送的时间间隔
        int interval = new Random().nextInt(8) + 1;
        System.out.println(interval + "秒后会向Server发送心跳");

        //获取定时器
        schedule = channel.eventLoop().schedule(() -> {
            if (channel.isActive()) {
                System.out.println("向Server发送心跳");
                channel.writeAndFlush("~PING~");
            } else {
                System.out.println("与Server间的连接已经关闭");
            }
        }, interval, TimeUnit.SECONDS);

        //添加监听到定时器中
        listener = (future) -> {
            // 再次发送心跳
            System.out.println("向Server再次发送心跳");
            sendHeartbeat(channel);
        };

        // 向定时器添加监听器
        schedule.addListener(listener);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 一旦连接被关闭，则将监听器移除，这样就不会再发生心跳方法的递归调用了，以防止栈溢出
        schedule.removeListener(listener);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
