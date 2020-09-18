package com.gupaoedu.vip.netty.rpc.provider;

import com.gupaoedu.vip.netty.rpc.IRpcHelloService;

public class RpcHelloServiceImpl implements IRpcHelloService {

    public String hello(String name) {  
        return "Hello " + name + "!";  
    }  
  
}  
