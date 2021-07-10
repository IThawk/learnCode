package com.kkb.mybatis.sqlsource.iface;

import com.kkb.mybatis.sqlsource.BoundSql;

/**
 * 提供对select/update/delete/insert标签内的SQL语句的处理
 * 
 * @author 灭霸詹
 *
 */
public interface SqlSource {

	/**
	 * 解析SqlSource实现类存储的SQL信息
	 * #{} :PreparedStatement处理方式
	 * 		SELECT * FROM user WHERE username = ? AND sex = ?
	 * ${}	:Statement的处理方式
	 * 		SELECT * FROM user WHERE username = "+username的值+" AND sex = "+sex的值
	 * @param param
	 * @return
	 */
	BoundSql getBoundSql(Object param);
}
