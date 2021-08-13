package org.apache.dubbo.demo.spi.service;

import org.apache.dubbo.common.extension.SPI;

/**
 * @author ithawk
 * @projectName dubbo
 * @description: TODO
 * @date 2021/8/128:58
 */
public class SpiServiceImpl implements SpiService{

    private DemoServiceImpl demoServiceImpl;

    //注意 使用这个方法名 setDemoServiceImpl-》》demoServiceImpl
    public void setDemoServiceImpl(DemoServiceImpl demoServiceImpl) {
        this.demoServiceImpl = demoServiceImpl;
    }

    @Override
    public void doSomeThing() {
        this.demoServiceImpl.sayHello();
    }
}
