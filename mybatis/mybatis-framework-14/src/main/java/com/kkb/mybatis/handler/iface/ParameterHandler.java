package com.kkb.mybatis.handler.iface;

import java.sql.Statement;

import com.kkb.mybatis.sqlsource.BoundSql;

public interface ParameterHandler {

	void setParameter(Statement statement, Object param, BoundSql boundSql) throws Exception;
}
