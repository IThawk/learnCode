package com.kkb.mybatis.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.kkb.mybatis.config.Configuration;
import com.kkb.mybatis.config.MappedStatement;
import com.kkb.mybatis.handler.iface.ParameterHandler;
import com.kkb.mybatis.handler.iface.ResultSetHandler;
import com.kkb.mybatis.handler.iface.StatementHandler;
import com.kkb.mybatis.sqlsource.BoundSql;

/**
 * 预编译的statement
 * 
 * @author 灭霸詹
 *
 */
public class PreparedStatementHandler implements StatementHandler {

	private ParameterHandler parameterHandler;
	private ResultSetHandler resultSetHandler;

	public PreparedStatementHandler(Configuration configuration) {
		parameterHandler = configuration.newParameterHandler();
		resultSetHandler = configuration.newResultSetHandler();
	}

	@Override
	public Statement prepare(Connection connection, String sql) throws Exception {
		return connection.prepareStatement(sql);
	}

	@Override
	public void parameterize(Statement statement, Object param, BoundSql boundSql) throws Exception {
		parameterHandler.setParameter(statement, param, boundSql);
	}

	@Override
	public List<Object> query(Statement statement, MappedStatement mappedStatement) throws Exception {
		PreparedStatement preparedStatement = (PreparedStatement) statement;
		ResultSet rs = preparedStatement.executeQuery();

		List<Object> results = resultSetHandler.handleResultSets(mappedStatement, rs);
		return results;
	}

}
