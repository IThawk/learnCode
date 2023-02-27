package com.baizhi.dao;

import com.baizhi.entity.Fruit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FruitDao {

    //查询所有
    List<Fruit> findAll();
}
