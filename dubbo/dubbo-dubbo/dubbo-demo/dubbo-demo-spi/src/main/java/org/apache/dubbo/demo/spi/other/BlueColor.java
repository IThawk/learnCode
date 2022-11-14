package org.apache.dubbo.demo.spi.other;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.demo.spi.activate.Color;

@Activate(value = "blueColor",group = "blue")
public class BlueColor implements MyColor {


    @Override
    public String color() {
        return "我的颜色是蓝色";
    }
}
