package com.ithawk.demo.mybatis.plugs.crud.dao;


import com.ithawk.demo.mybatis.plugs.crud.bean.HawkObject;

import java.util.List;

public interface HawkObjectMapper {
    List<HawkObject> selectById(String id);
}