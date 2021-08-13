package org.apache.dubbo.demo.spi.service;

import org.apache.dubbo.common.extension.SPI;

/**
 * @author ithawk
 * @projectName dubbo
 * @description: TODO
 * @date 2021/8/128:58
 */
@SPI()
public interface SpiService {
    void doSomeThing();
}
