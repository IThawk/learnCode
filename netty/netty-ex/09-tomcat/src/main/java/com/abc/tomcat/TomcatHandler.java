package com.abc.tomcat;

import com.abc.servnet.NettyRequest;
import com.abc.servnet.NettyResponse;
import com.abc.servnet.Servnet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

import java.util.Map;

/**
 * Tomcat服务端处理器
 *
 *   1）从用户请求URI中解析出要访问的Servnet名称
 *   2）从nameToServnetMap中查找是否存在该名称的key。若存在，则直接使用该实例，否则执行第3）步
 *   3）从nameToClassNameMap中查找是否存在该名称的key，若存在，则获取到其对应的全限定性类名，
 *      使用反射机制创建相应的sernet实例，并写入到nameToServnetMap中，若不存在，则直接访问默认Servnet
 *
 */
public class TomcatHandler extends ChannelInboundHandlerAdapter {
    private Map<String, Servnet> nameToServnetMap;
    private Map<String, String> nameToClassNameMap;

    public TomcatHandler(Map<String, Servnet> nameToServnetMap, Map<String, String> nameToClassNameMap) {
        this.nameToServnetMap = nameToServnetMap;
        this.nameToClassNameMap = nameToClassNameMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            // 从请求中解析出要访问的Servnet名称
            String servnetName = request.uri().split("/")[1];

            Servnet servnet = new DefaultServnet();
            if (nameToServnetMap.containsKey(servnetName)) {
                servnet = nameToServnetMap.get(servnetName);
            } else if (nameToClassNameMap.containsKey(servnetName)) {
                // double-check，双重检测锁
                if (nameToServnetMap.get(servnetName) == null) {
                    synchronized (this) {
                        if (nameToServnetMap.get(servnetName) == null) {
                            // 获取当前Servnet的全限定性类名
                            String className = nameToClassNameMap.get(servnetName);
                            // 使用反射机制创建Servnet实例
                            servnet = (Servnet) Class.forName(className).newInstance();
                            // 将Servnet实例写入到nameToServnetMap
                            nameToServnetMap.put(servnetName, servnet);
                        }
                    }
                }
            } //  end-else if

            // 代码走到这里，servnet肯定不空
            NettyRequest req = new DefaultNettyRequest(request);
            NettyResponse res = new DefaultNettyResponse(request, ctx);
            // 根据不同的请求类型，调用servnet实例的不同方法
            if (request.method().name().equalsIgnoreCase("GET")) {
                servnet.doGet(req, res);
            } else if(request.method().name().equalsIgnoreCase("POST")) {
                servnet.doPost(req, res);
            }
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
