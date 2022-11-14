package org.apache.dubbo.demo.spi.other;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.demo.spi.activate.Color;

@Activate(value = "redColor")
public class RedColor implements MyColor {


    @Override
    public String color() {
        return "我的颜色是红色";
    }
}
