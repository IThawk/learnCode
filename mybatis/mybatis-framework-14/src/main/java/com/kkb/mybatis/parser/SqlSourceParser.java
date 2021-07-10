package com.kkb.mybatis.parser;

import com.kkb.mybatis.sqlsource.StaticSqlSource;
import com.kkb.mybatis.sqlsource.iface.SqlSource;
import com.kkb.mybatis.utils.GenericTokenParser;
import com.kkb.mybatis.utils.ParameterMappingTokenHandler;

/**
 * 主要用来解析#{}
 * 
 * @author 灭霸詹
 *
 */
public class SqlSourceParser {

	public SqlSource parse(String sql) {
		// 主要来处理${}中的参数名称，从入参对象中获取对应的参数值
		ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
		// 根据${ 和 }去截取字符串，最终匹配到的${}中的内容，交给TokenHandler去处理
		GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", handler);
		// 执行解析过程，返回值是处理完${}之后的值
		sql = tokenParser.parse(sql);
		System.out.println("解析#{}之前的SQL语句：" + sql);
		return new StaticSqlSource(sql, handler.getParameterMappings());
	}
}
