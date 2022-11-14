package com.kkb.mybatis.handler;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.kkb.mybatis.handler.iface.ParameterHandler;
import com.kkb.mybatis.sqlsource.BoundSql;
import com.kkb.mybatis.sqlsource.ParameterMapping;
import com.kkb.mybatis.utils.SimpleTypeRegistry;

/**
 * 专门来处理参数设置的类
 * @author 灭霸詹
 *
 */
public class DefaultParameterHandler implements ParameterHandler {

	@SuppressWarnings("rawtypes")
	@Override
	public void setParameter(Statement statement, Object param, BoundSql boundSql) throws Exception {
		PreparedStatement preparedStatement = (PreparedStatement) statement;
		if (SimpleTypeRegistry.isSimpleType(param.getClass())) {
			// 只要参数类型是符合这个条件的，那么说明入参就是一个基本类型的参数或者String类型的参数
			preparedStatement.setObject(1, param);
		} else if (param instanceof Map) {
			Map map = (Map) param;

			List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				String paramName = parameterMapping.getName();
				Object value = map.get(paramName);
				preparedStatement.setObject(i + 1, value);
			}

		} else {
			// ....
		}
	}

}
