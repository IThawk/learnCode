package org.apache.dubbo.demo.spi.activate;

import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI("blueColor")
public interface Color {

    void sayMy();


    void sayHello();

}
