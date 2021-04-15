package com.ithawk.demo.netty.tomcat.webapp;


import com.ithawk.demo.netty.tomcat.servnet.NettyRequest;
import com.ithawk.demo.netty.tomcat.servnet.NettyResponse;
import com.ithawk.demo.netty.tomcat.servnet.Servnet;

/**
 * 业务Servnet
 */
public class OneServnet extends Servnet {
    @Override
    public void doGet(NettyRequest request, NettyResponse response) throws Exception {
        String uri = request.getUri();
        System.out.println(uri);
        String path = request.getPath();
        String method = request.getMethod();
        String name = request.getParameter("name");

        String content = "uri = " + uri + "\n" +
                         "path = " + path + "\n" +
                         "method = " + method + "\n" +
                         "param = " + name;
        response.write(content);
    }

    @Override
    public void doPost(NettyRequest request, NettyResponse response) throws Exception {
        doGet(request, response);
    }
}
