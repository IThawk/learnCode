package com.kkb.mybatis.factory;

import com.kkb.mybatis.config.Configuration;
import com.kkb.mybatis.sqlsession.DefaultSqlSession;
import com.kkb.mybatis.sqlsession.SqlSession;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

	private Configuration configuration;
	
	public DefaultSqlSessionFactory(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public SqlSession openSession() {
		return new DefaultSqlSession(configuration);
	}

}
