package org.apache.dubbo.demo.spi.wrapper;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;

@Activate(order = 4,value = "wrapper")
public class WrapperOrder implements Order {

    private Order order;

    public WrapperOrder(Order order) {
        System.out.println("这个是增强类 的构造函数");
        this.order = order;
    }


    @Override
    public String way() {
        System.out.println("这个是增强类 before");
        String way =  order.way();
        System.out.println("这个是增强类 after");
        return way;
    }

    @Override
    public String pay(URL url) {
        System.out.println("这个是增强类 before");
        String way =  order.pay(url);
        System.out.println("这个是增强类 after");
        return way;
    }
}
