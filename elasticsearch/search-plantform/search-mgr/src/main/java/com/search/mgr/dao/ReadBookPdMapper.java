package com.search.mgr.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.search.mgr.core.Mapper;
import com.search.mgr.model.ReadBookPd;

public interface ReadBookPdMapper extends Mapper<ReadBookPd> {

	public List<ReadBookPd> getPageList(@Param("start") Integer start, @Param("size") Integer size);

	List<Map<String, Object>> buildESQuery(@Param("id") Integer id);
}