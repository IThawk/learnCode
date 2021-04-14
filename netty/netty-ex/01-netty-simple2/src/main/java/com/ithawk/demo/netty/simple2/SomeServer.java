package com.ithawk.demo.netty.simple2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

// 服务器启动类
public class SomeServer {
    public static void main(String[] args) throws InterruptedException {

        // 用于处理客户端连接请求，将请求发送给childGroup中的eventLoop
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        // 用于处理客户端请求
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            // 用户启动ServerChannel
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)  // 指定eventLoopGroup
                    .channel(NioServerSocketChannel.class)  // 指定使用NIO进行通信
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        // 当Channel初始创建完毕后就会触发该方法的执行，用于初始化Channel
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 从Channel中获取pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            // 将HttpServerCodec处理器放入到pipeline的最后
                            // HttpServerCodec是什么？是HttpRequestDecoder与HttpResponseEncoder的复合体
                            // HttpRequestDecoder：http请求解码器，将Channel中的ByteBuf数据解码为HttpRequest对象
                            // HttpResponseEncoder：http响应编码器，将HttpResponse对象编码为将要在Channel中发送的ByteBuf数据
                            pipeline.addLast(new HttpServerCodec());
                            // 将自再定义的处理器放入到Pipeline的最后
                            pipeline.addLast(new SomeServerHandler());
                        }
                    });   // 指定childGroup中的eventLoop所绑定的线程所要处理的处理器

            // 指定当前服务器所监听的端口号
            // bind()方法的执行是异步的
            // sync()方法会使bind()操作与后续的代码的执行由异步变为了同步
            ChannelFuture future = bootstrap.bind(8888).sync();
            System.out.println("服务器启动成功。监听的端口号为：8888");
            // 关闭Channel
            // closeFuture()的执行是异步的。
            // 当Channel调用了close()方法并关闭成功后才会触发closeFuture()方法的执行
            future.channel().closeFuture().sync();
        } finally {
            // 优雅关闭
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
