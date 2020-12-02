package com.ithawk.demo.mybatis.v1.crud.dao;


import com.ithawk.demo.mybatis.v1.crud.bean.HawkObject;

import java.util.List;

public interface HawkObjectMapper {
    List<HawkObject> selectById(String id);
}