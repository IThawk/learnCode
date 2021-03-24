package org.apache.dubbo.demo.spi;

import org.apache.dubbo.common.extension.Activate;

@Activate(value = "red")
public class RedColor implements Color {


    @Override
    public void sayMy() {
        System.out.println("我是红色的呀！你喜欢吗?");
    }

    @Override
    public void sayHello() {
        System.out.println("流血的痛苦！");
    }

}
