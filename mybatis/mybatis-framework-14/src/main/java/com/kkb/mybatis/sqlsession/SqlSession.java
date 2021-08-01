package com.kkb.mybatis.sqlsession;

import java.util.List;

public interface SqlSession {
	/**
	 * 查询单条记录
	 * @param statementId
	 * @param param
	 * @return
	 */
	<T> T selectOne(String statementId, Object param);

	/**
	 * 查询多条记录
	 * @param statementId
	 * @param param
	 * @return
	 */
	<T> List<T> selectList(String statementId, Object param);
}
