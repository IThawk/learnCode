package org.apache.dubbo.demo.spi.other;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface MyColor {
    String color();
}
