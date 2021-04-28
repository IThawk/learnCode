package com.ithawk.demo.netty.tomcat.tomcat;


import com.ithawk.demo.netty.tomcat.servnet.NettyRequest;
import com.ithawk.demo.netty.tomcat.servnet.NettyResponse;
import com.ithawk.demo.netty.tomcat.servnet.Servnet;

/**
 * Tomcat中对Servnet规范的默认实现
 */
public class DefaultServnet extends Servnet {
    @Override
    public void doGet(NettyRequest request, NettyResponse response) throws Exception {
        // http://localhost:8888/someservnet/xxx/ooo?name=zs
        // uri：/someservnet/xxx/ooo?name=zs
        // path：/someservnet/xxx/ooo
        String sernetName = request.getUri().split("/")[1];
        response.write("404 - no this servnet : " + sernetName);
    }

    @Override
    public void doPost(NettyRequest request, NettyResponse response) throws Exception {
        doGet(request, response);
    }
}
