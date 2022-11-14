package com.kkb.mybatis.executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import com.kkb.mybatis.config.Configuration;
import com.kkb.mybatis.config.MappedStatement;
import com.kkb.mybatis.handler.iface.StatementHandler;
import com.kkb.mybatis.sqlsource.BoundSql;

public class SimpleExecutor extends BaseExecutor {

	@Override
	public List<Object> queryFromDataBase(String statementId, Object param, Configuration configuration,
			BoundSql boundSql) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connection = getConnection(configuration.getDataSource());

			// 定义sql语句 ?表示占位符
			// 先要获取MappedStatement
			MappedStatement mappedStatement = configuration.getMappedStatementById(statementId);
			// 通过BoundSql获取到SQL语句
			String sql = boundSql.getSql();

			String statementType = mappedStatement.getStatementType();
			StatementHandler statementHandler = configuration.newStatementHandler(statementType);

			// 获取预处理 statement
			Statement statement = statementHandler.prepare(connection, sql);

			// 设置参数，第一个参数为 sql 语句中参数的序号（从 1 开始），第二个参数为设置的
			// 参数处理
			statementHandler.parameterize(statement, param, boundSql);

			// 向数据库发出 sql 执行查询，查询出结果集
			List<Object> results = statementHandler.query(statement, mappedStatement);

			return results;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
		return null;
	}

	private Connection getConnection(DataSource dataSource) {
		try {
			Connection connection = dataSource.getConnection();
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
