package com.kkb.mybatis.sqlsession;

import java.util.List;

import com.kkb.mybatis.config.Configuration;
import com.kkb.mybatis.executor.iface.Executor;

public class DefaultSqlSession implements SqlSession {

	private Configuration configuration;

	public DefaultSqlSession(Configuration configuration) {
		this.configuration = configuration;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T selectOne(String statementId, Object param) {
		List<Object> list = this.selectList(statementId, param);
		if (list != null && list.size() == 1) {
			return (T) list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> selectList(String statementId, Object param) {
		// CachingExecutor--委托模式-->BaseExecutor--抽象模板方法模式-->SimpleExecutor
		Executor executor = configuration.newExecutor();
		return (List<T>) executor.query(statementId, param, configuration);
	}

}
