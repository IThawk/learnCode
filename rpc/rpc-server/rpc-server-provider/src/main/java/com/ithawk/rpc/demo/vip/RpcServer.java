package com.ithawk.rpc.demo.vip;


import com.ithawk.rpc.demo.vip.registry.IRegistryCenter;
import com.ithawk.rpc.demo.vip.registry.RegistryCenterWithZk;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * 这里使用到spring进行容器化管理服务
 * InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候都会执行该方法。
 *
 * Spring容器会检测容器中的所有Bean，如果发现某个Bean实现了ApplicationContextAware接口，Spring容器会在创建该Bean之后，
 * 自动调用该Bean的setApplicationContextAware()方法，调用该方法时，
 * 会将容器本身作为参数传给该方法——该方法中的实现部分将Spring传入的参数（容器本身）赋给该类对象的applicationContext实例变量，
 * 因此接下来可以通过该applicationContext实例变量来访问容器本身。
 *
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {

    private Map<String, Object> handlerMap = new HashMap();

    private int port;

    private IRegistryCenter registryCenter = new RegistryCenterWithZk();

    public RpcServer(int port) {
        this.port = port;
    }

    /**
     * 这个的功能是在spring初始化完成所有的bean之后，执行
     * 使用netty 进行服务的发布
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        /**
         * netty的编程步骤：
         *  1：启动主线程：EventLoopGroup bossGroup = new NioEventLoopGroup();
         *  2：启动工作线程：EventLoopGroup workerGroup = new NioEventLoopGroup();
         *  3：线程绑定服务：ServerBootstrap serverBootstrap = new ServerBootstrap();
         *             serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
         *  4：添加服务出来handler 处理服务请求: childHandler(new ChannelInitializer<SocketChannel>() {
         *                         @Override
         *                         protected void initChannel(SocketChannel socketChannel) throws Exception {
         *                             socketChannel.pipeline().
         *                             //添加服务反序列化
         *                                     addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null))).
         *                             //添加序列化
         *                                     addLast(new ObjectEncoder()).
         *                             //具体的处理
         *                                     addLast(new ProcessorHandler(handlerMap));
         *                         }
         *                     });
         */
        //接收客户端的链接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理已经被接收的链接
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
                    childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().
                                    addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null))).
                                    addLast(new ObjectEncoder()).
                                    addLast(new ProcessorHandler(handlerMap));
                        }
                    });
            serverBootstrap.bind(port).sync();
        } finally {
           /* workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();*/
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (!serviceBeanMap.isEmpty()) {
            for (Object servcieBean : serviceBeanMap.values()) {
                //拿到注解
                RpcService rpcService = servcieBean.getClass().getAnnotation((RpcService.class));
                String serviceName = rpcService.value().getName();//拿到接口类定义
                String version = rpcService.version(); //拿到版本号
                if (!StringUtils.isEmpty(version)) {
                    serviceName += "-" + version;
                }
                handlerMap.put(serviceName, servcieBean);
                //将服务信息注册到注册中心
                registryCenter.registry(serviceName, getAddress() + ":" + port);
            }
        }
    }

    private static String getAddress() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return inetAddress.getHostAddress();// 获得本机的ip地址
    }
}
