package org.apache.dubbo.demo.spi.service;

import org.springframework.stereotype.Service;

/**
 * @author ithawk
 * @projectName dubbo
 * @description: TODO
 * @date 2021/8/128:58
 */
@Service("demoService")
public class DemoServiceImpl implements DemoService{
    @Override
    public void sayHello() {
        System.out.println("122222");
    }
}
