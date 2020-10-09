package com.ithawk.demo.spring.v1.pattern.decorator;

/**
 * @Author: qingshan
 * @Date: 2019/3/11 00:34
 * @Description: 咕泡学院，只为更好的你
 */
public class LemonTea extends Drink{
    public LemonTea() {
        desc = "柠檬茶";
    }

    public double cost() {
        return 10;
    }
}
