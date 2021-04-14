package com.abc.registry;


public class RegisterTest {
    public static void main(String[] args) throws Exception {
        RegistryCenter center = new ZKRegistryCenter();
        center.register("com.abc.service.SomeService2", "localhost:8888");
        System.in.read();
    }
}
