package org.apache.dubbo.demo.spi.wrapper;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;

/**
 * 控制执行顺序 order越小 顺序越早
 */
@Activate(order = 3,value = "wrapper2")
public class Wrapper2Order implements Order {

    
    private Order order;

    public Wrapper2Order(Order order) {
        System.out.println("这个是增强类2 的构造函数");
        this.order = order;
    }

    @Override
    public String way() {
        System.out.println("这个是增强类2 before");
        String way =  order.way();
        System.out.println("这个是增强类2 after");
        return way;
    }

    @Override
    public String pay(URL url) {
        System.out.println("这个是增强类2 --pay --before");
        String way =  order.pay(url);
        System.out.println("这个是增强类2-- pay --after");
        return way;
    }
}
