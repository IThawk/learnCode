package com.ithawk.demo.pattern.proxy.simpleproxy;

/**
 * 静态代理
 */
public class Client {

    public static void main(String[] args) {

        Proxy proxy = new Proxy(new RealSubject());
        proxy.request();

    }

}
