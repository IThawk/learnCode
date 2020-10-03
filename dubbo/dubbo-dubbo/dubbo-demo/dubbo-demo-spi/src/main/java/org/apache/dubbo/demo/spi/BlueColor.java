package org.apache.dubbo.demo.spi;

import org.apache.dubbo.common.extension.Activate;

@Activate(value = "cache")
public class BlueColor implements Color {


    @Override
    public void sayMy() {
        System.out.println("我是蓝色的呀！你喜欢吗?");
    }

    @Override
    public void sayHello() {
        System.out.println("红红火火的对你说喜欢你！");
    }

}
