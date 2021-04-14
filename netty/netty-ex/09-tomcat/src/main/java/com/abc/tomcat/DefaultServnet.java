package com.abc.tomcat;

import com.abc.servnet.NettyRequest;
import com.abc.servnet.NettyResponse;
import com.abc.servnet.Servnet;

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
