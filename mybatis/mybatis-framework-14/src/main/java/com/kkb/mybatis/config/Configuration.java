package com.kkb.mybatis.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.kkb.mybatis.executor.CachingExecutor;
import com.kkb.mybatis.executor.SimpleExecutor;
import com.kkb.mybatis.executor.iface.Executor;
import com.kkb.mybatis.handler.DefaultParameterHandler;
import com.kkb.mybatis.handler.DefaultResultSetHandler;
import com.kkb.mybatis.handler.PreparedStatementHandler;
import com.kkb.mybatis.handler.iface.ParameterHandler;
import com.kkb.mybatis.handler.iface.ResultSetHandler;
import com.kkb.mybatis.handler.iface.StatementHandler;

/**
 * 封装了全局配置文件和映射文件
 * 
 * @author 灭霸詹
 *
 */
public class Configuration {

	private DataSource dataSource;

	private Map<String, MappedStatement> mappedStatements = new HashMap<>();

	private boolean useCache = true;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public MappedStatement getMappedStatementById(String statementId) {
		return mappedStatements.get(statementId);
	}

	public void addMappedStatement(String statementId, MappedStatement mappedStatement) {
		this.mappedStatements.put(statementId, mappedStatement);
	}

	public Executor newExecutor() {
		// TODO
		Executor executor = null;
		// 如果参数是simple
		executor = new SimpleExecutor();
		// 如果启用缓存，则Executor为CachingExecutor
		if (useCache) {
			// 对Executor进行缓存功能的装饰
			executor = new CachingExecutor(executor);
		}
		return executor;
	}

	public StatementHandler newStatementHandler(String statementType) {
		if (statementType.equals("prepared")) {
			return new PreparedStatementHandler(this);
		}
		return null;
	}

	public ParameterHandler newParameterHandler() {
		return new DefaultParameterHandler();
	}

	public ResultSetHandler newResultSetHandler() {
		return new DefaultResultSetHandler();
	}
}
