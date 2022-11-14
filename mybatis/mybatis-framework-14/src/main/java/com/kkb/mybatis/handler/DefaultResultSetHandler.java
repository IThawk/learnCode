package com.kkb.mybatis.handler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import com.kkb.mybatis.config.MappedStatement;
import com.kkb.mybatis.handler.iface.ResultSetHandler;

/**
 * 专门用来处理结果集的处理类
 * 
 * @author 灭霸詹
 *
 */
public class DefaultResultSetHandler implements ResultSetHandler {

	@Override
	public List<Object> handleResultSets(MappedStatement mappedStatement, ResultSet rs) throws Exception {
		List<Object> results = new ArrayList<Object>();
		Class<?> resultType = mappedStatement.getResultTypeClass();
		while (rs.next()) {
			// 结果处理
			Object result = resultType.newInstance();

			ResultSetMetaData metaData = rs.getMetaData();
			// 获取结果集每一行有多少列
			int columnCount = metaData.getColumnCount();

			for (int i = 1; i <= columnCount; i++) {
				// 获取列的名称
				String columnName = metaData.getColumnName(i);
				// 要求：SELECT中显示的列名要和类中的属性名完全一致
				Field field = resultType.getDeclaredField(columnName);
				field.setAccessible(true);
				field.set(result, rs.getObject(columnName));
			}

			results.add(result);
		}
		return results;
	}

}
