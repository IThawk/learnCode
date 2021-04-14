package com.abc.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServerStarter {
    public static void main(String[] args) throws Exception {
        List<Integer> ports = new ArrayList<>();
        ports.add(7777);
        ports.add(8888);
        ports.add(9999);

        SomeServer server = new SomeServer();
        // 启动服务器，按照指定端口号进行监听
        server.start(ports);

        // 30秒后关闭所有channel
        TimeUnit.SECONDS.sleep(60);
        server.closeAllChannel();
    }
}
