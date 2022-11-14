package com.ithawk.demo.cache.ithawk.dao;


import com.ithawk.demo.cache.ithawk.bean.HawkObject;

import java.util.List;

public interface HawkObjectMapper {
    List<HawkObject> selectById(String id);
}