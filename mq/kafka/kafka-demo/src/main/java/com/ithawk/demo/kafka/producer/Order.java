package com.ithawk.demo.kafka.producer;

/**
 * @author ithawk
 * @projectName mq
 * @description: TODO
 * @date 2021/12/2810:10
 */
public class Order {
    int orderId;

    double price;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Order(int i, int i1, int i2, double price) {
        this.price = price;
        this.orderId = i;
    }
}
