package org.apache.dubbo.demo.spi.activate;

import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI("color")
public interface Color {

    void sayMy();

    @Adaptive
    void sayHello();

}
