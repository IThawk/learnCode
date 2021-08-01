package com.kkb.mybatis.handler;

import java.sql.Connection;
import java.util.List;

import com.kkb.mybatis.config.MappedStatement;
import com.kkb.mybatis.handler.iface.StatementHandler;
import com.kkb.mybatis.sqlsource.BoundSql;

public class SimpleStatementHandler implements StatementHandler {

	@Override
	public java.sql.Statement prepare(Connection connection, String sql) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parameterize(java.sql.Statement statement, Object param, BoundSql boundSql) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Object> query(java.sql.Statement statement, MappedStatement mappedStatement) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
