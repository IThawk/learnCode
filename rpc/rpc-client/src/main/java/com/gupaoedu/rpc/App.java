package com.gupaoedu.rpc;

import com.gupaoedu.vip.IHelloService;
import com.gupaoedu.vip.IPaymentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        ApplicationContext context=new
                AnnotationConfigApplicationContext(SpringConfig.class);
        RpcProxyClient rpcProxyClient=context.getBean(RpcProxyClient.class);

        IHelloService iHelloService=rpcProxyClient.clientProxy
                (IHelloService.class,"v2.0");
        for(int i=0;i<100;i++) {
            Thread.sleep(2000);
            System.out.println(iHelloService.sayHello(1.0));
        }
    }
}
