package org.apache.dubbo.demo.provider;

import org.apache.dubbo.common.extension.SPI;

/**
 * 下单接口
 */
@SPI("alipay")
public interface Order {
    // 支付方式
    String way();
}
