package com.ithawk.mybatis.demo.vip.netty.rpc.provider;

import com.ithawk.mybatis.demo.vip.netty.rpc.IRpcHelloService;

public class RpcHelloServiceImpl implements IRpcHelloService {

    public String hello(String name) {  
        return "Hello " + name + "!";  
    }  
  
}  
