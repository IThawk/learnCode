package com.kkb.mybatis.framework.utils;

import java.math.BigDecimal;

import ognl.Ognl;
import ognl.OgnlContext;

public class OgnlUtils {
	/**
	 * 根据Ongl表达式，获取指定对象的参数值
	 * @param expression
	 * @param paramObject
	 * @return
	 */
	public static Object getValue(String expression, Object paramObject) {
		try {
			OgnlContext context = new OgnlContext();
			context.setRoot(paramObject);

			//mybatis中的动态标签使用的是ognl表达式
			//mybatis中的${}使用的是ognl表达式
			Object ognlExpression = Ognl.parseExpression(expression);// 构建Ognl表达式
			Object value = Ognl.getValue(ognlExpression, context, context.getRoot());// 解析表达式

			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过Ognl表达式，去计算boolean类型的结果
	 * @param expression
	 * @param parameterObject
	 * @return
	 */
	public static boolean evaluateBoolean(String expression, Object parameterObject) {
		Object value = OgnlUtils.getValue(expression, parameterObject);
		if (value instanceof Boolean) {
			return (Boolean) value;
		}
		if (value instanceof Number) {
			return new BigDecimal(String.valueOf(value)).compareTo(BigDecimal.ZERO) != 0;
		}
		return value != null;
	}
}
