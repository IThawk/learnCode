package com.ithawk.demo.zookeeper.springboot.controller;

import com.ithawk.demo.zookeeper.springboot.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api("TestController")
public class TestController {


    @Autowired
    private OrderService orderService;

    @Value("${server.port}")
    private String port;


    @Autowired
    CuratorFramework curatorFramework;

    @PostMapping("/stock/deduct")
    @ApiOperation("减少库存")
    public Object reduceStock(Integer id) throws Exception {

        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, "/product_" + id);

        try {
            // 获取锁
            interProcessMutex.acquire();
            orderService.reduceStock(id);

        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw e;
            }
        } finally {
            interProcessMutex.release();
        }
        return "ok:" + port;
    }


}
