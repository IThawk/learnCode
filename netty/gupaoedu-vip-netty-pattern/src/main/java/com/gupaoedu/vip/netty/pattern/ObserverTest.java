package com.gupaoedu.vip.netty.pattern;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by Tom.
 */
public class ObserverTest {

    public static void main(String[] args) {



    }

    public void write(NioSocketChannel channel,Object object){
        ChannelFuture channelFuture = channel.writeAndFlush(object);
        channelFuture.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){

                }else{

                }
            }
        });
    }
}
