package com.ithawk.demo.mybatis.sharding.v1.dto;

import com.ithawk.demo.mybatis.sharding.v1.entity.Order;
import com.ithawk.demo.mybatis.sharding.v1.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class OrderInfoDto {

    private List<Order> order;

    private List<OrderItem> item;

}
