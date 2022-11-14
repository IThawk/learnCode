package com.kkb.mybatis.handler.iface;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import com.kkb.mybatis.config.MappedStatement;
import com.kkb.mybatis.sqlsource.BoundSql;

/**
 * 去处理关于Statement相对的逻辑
 * 
 * @author 灭霸詹
 *
 */
public interface StatementHandler {

	/**
	 * 创建Statement
	 * 
	 * @param connection
	 * @param sql
	 * @return
	 */
	Statement prepare(Connection connection, String sql) throws Exception;

	/**
	 * 设置参数
	 * 
	 * @param statement
	 * @param param
	 * @param boundSql
	 */
	void parameterize(Statement statement, Object param, BoundSql boundSql) throws Exception;

	/**
	 * 查询操作
	 * 
	 * @param statement
	 * @return
	 */
	List<Object> query(Statement statement, MappedStatement mappedStatement) throws Exception;

}
