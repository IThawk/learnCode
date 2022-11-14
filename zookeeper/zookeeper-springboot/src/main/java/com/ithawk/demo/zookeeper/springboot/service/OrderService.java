package com.ithawk.demo.zookeeper.springboot.service;

import com.alibaba.fastjson.JSON;
import com.ithawk.demo.zookeeper.springboot.entity.Order;
import com.ithawk.demo.zookeeper.springboot.entity.Product;
import com.ithawk.demo.zookeeper.springboot.mapper.OrderMapper;
import com.ithawk.demo.zookeeper.springboot.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class OrderService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;


    @Transactional
    public void reduceStock(Integer id) {
        // 1.    获取库存
        Product product = productMapper.getProduct(id);
        log.info("getProduct:{}", JSON.toJSONString(product));
        // 模拟耗时业务处理
        sleep(500); // 其他业务处理

        if (product.getStock() <= 0) {
            throw new RuntimeException("out of stock");
        }
        // 2.    减库存
        int i = productMapper.deductStock(id);
        if (i == 1) {
            Order order = new Order();
            order.setUserId(UUID.randomUUID().toString());
            order.setPid(id);
            orderMapper.insert(order);
        } else {
            throw new RuntimeException("deduct stock fail, retry.");
        }

    }

    /**
     * 模拟耗时业务处理
     *
     * @param wait
     */
    public void sleep(long wait) {
        try {
            TimeUnit.MILLISECONDS.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
