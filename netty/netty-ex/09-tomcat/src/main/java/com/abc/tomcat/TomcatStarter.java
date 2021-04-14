package com.abc.tomcat;

public class TomcatStarter {
    public static void main(String[] args) throws Exception {
        TomcatServer server = new TomcatServer("com.abc.webapp");
        server.start();
    }
}
