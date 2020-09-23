package com.ithawk.rpc.demo.vip;


@RpcService(value = IHelloService.class,version = "v1.0")
public class HelloServiceImpl implements IHelloService{

    @Override
    public String sayHello(double money) {
        System.out.println("【V1.0】request in sayHello:"+money);
        return "【V1.0】Say Hello:"+money;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("【V1.0】request in saveUser:"+user);
        return "【V1.0】SUCCESS";
    }
}
