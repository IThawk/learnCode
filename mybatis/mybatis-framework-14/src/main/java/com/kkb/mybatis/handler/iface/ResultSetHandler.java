package com.kkb.mybatis.handler.iface;

import java.sql.ResultSet;
import java.util.List;

import com.kkb.mybatis.config.MappedStatement;

public interface ResultSetHandler {

	List<Object> handleResultSets(MappedStatement mappedStatement,ResultSet rs) throws Exception;
}
