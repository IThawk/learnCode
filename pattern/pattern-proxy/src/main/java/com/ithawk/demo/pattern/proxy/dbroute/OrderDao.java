package com.ithawk.demo.pattern.proxy.dbroute;

/**
 *
 */
public class OrderDao {
    public int insert(Order order) {
        System.out.println("OrderDao创建Order成功!");
        return 1;
    }
}
