package com.ithawk.demo.netty.tomcat.tomcat;

/**
 * http://127.0.0.1:8888/oneservnet/ss?name=123
 */
public class TomcatStarter {
    public static void main(String[] args) throws Exception {
        TomcatServer server = new TomcatServer("com.ithawk.demo.netty.tomcat.webapp");
        server.start();
    }
}
