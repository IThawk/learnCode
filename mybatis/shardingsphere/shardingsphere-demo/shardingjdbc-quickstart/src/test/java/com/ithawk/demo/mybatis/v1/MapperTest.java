package com.ithawk.demo.mybatis.v1;


import com.ithawk.demo.mybatis.sharding.v1.TlShopApplication;
import com.ithawk.demo.mybatis.sharding.v1.entity.Order;
import com.ithawk.demo.mybatis.sharding.v1.mapper.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

import com.alibaba.fastjson.JSON;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TlShopApplication.class)
public class MapperTest {


    @Autowired
    OrderMapper orderMapper;

    @Test
    public void testSelectAll() {
        try {
            List<Order> orderList = orderMapper.selectAll();
            System.out.println(JSON.toJSONString(orderList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSelectRange() {
        try {
            List<Order> orderList = orderMapper.selectRange(200000000000000000L,400000000000000000L);
            System.out.println(JSON.toJSONString(orderList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
