package com.kkb.mybatis.framework.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.kkb.mybatis.framework.config.MappedStatement;

/**
 * 封装了全局配置文件和映射文件
 * 
 * @author 灭霸詹
 *
 */
public class Configuration {

	private DataSource dataSource;

	private Map<String, MappedStatement> mappedStatements = new HashMap<>();

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

}
