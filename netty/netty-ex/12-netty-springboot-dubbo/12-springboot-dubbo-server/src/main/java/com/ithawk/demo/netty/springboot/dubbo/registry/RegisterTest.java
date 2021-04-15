package com.ithawk.demo.netty.springboot.dubbo.registry;


public class RegisterTest {
    public static void main(String[] args) throws Exception {
        RegistryCenter center = new ZKRegistryCenter();
        center.register("com.ithawk.demo.netty.springboot.dubbo.service.SomeService2", "localhost:8888");
        System.in.read();
    }
}
