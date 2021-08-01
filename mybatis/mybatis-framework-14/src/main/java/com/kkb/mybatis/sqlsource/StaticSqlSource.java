package com.kkb.mybatis.sqlsource;

import java.util.List;

import com.kkb.mybatis.sqlsource.iface.SqlSource;

/**
 * 封装不带有${}和动态标签的整个SQL信息
 * 
 * @author 灭霸詹
 *
 */
public class StaticSqlSource implements SqlSource {

	private String sql;

	private List<ParameterMapping> parameterMappings;

	public StaticSqlSource(String sql, List<ParameterMapping> parameterMappings) {
		this.sql = sql;
		this.parameterMappings = parameterMappings;
	}

	@Override
	public BoundSql getBoundSql(Object param) {
		return new BoundSql(sql, parameterMappings);
	}

}