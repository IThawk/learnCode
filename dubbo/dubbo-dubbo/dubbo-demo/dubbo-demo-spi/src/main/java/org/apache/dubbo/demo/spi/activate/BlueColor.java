package org.apache.dubbo.demo.spi.activate;

import org.apache.dubbo.common.extension.Activate;

@Activate(value = "blueColor",group = "blue")
public class BlueColor implements Color {


    @Override
    public void sayMy() {
        System.out.println("我是蓝色的呀！你喜欢吗?");
    }

    @Override
    public void sayHello() {
        System.out.println("蓝色的对你说喜欢你！");
    }

}
