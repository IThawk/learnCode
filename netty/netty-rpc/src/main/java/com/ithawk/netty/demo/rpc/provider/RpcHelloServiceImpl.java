package com.ithawk.netty.demo.rpc.provider;

import com.ithawk.netty.demo.rpc.IRpcHelloService;

public class RpcHelloServiceImpl implements IRpcHelloService {

    public String hello(String name) {  
        return "Hello " + name + "!";  
    }  
  
}  
