package org.apache.dubbo.demo.spi.activate;

import org.apache.dubbo.common.extension.Activate;

@Activate(order = 3,value = "wrapper")
public class WrapperColor implements Color {

    private Color color;

    public WrapperColor(Color color) {
        this.color = color;
    }

    @Override
    public void sayMy() {
        System.out.println("我是包装类---颜色");

        color.sayMy();
    }

    @Override
    public void sayHello() {
        System.out.println("我是包装类---hello");

        color.sayHello();
    }

}
