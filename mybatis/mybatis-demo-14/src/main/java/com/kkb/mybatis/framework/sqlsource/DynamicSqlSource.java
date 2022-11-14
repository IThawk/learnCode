package com.kkb.mybatis.framework.sqlsource;

import com.kkb.mybatis.framework.sqlnode.support.DynamicContext;
import com.kkb.mybatis.framework.sqlsource.iface.SqlSource;
import com.kkb.mybatis.framework.sqlnode.MixedSqlNode;
import com.kkb.mybatis.framework.sqlsource.BoundSql;
import com.kkb.mybatis.framework.utils.GenericTokenParser;
import com.kkb.mybatis.framework.utils.ParameterMappingTokenHandler;

/**
 * 封装带有${}或者动态标签的整个SQL信息 处理之前的SQL语句：SELECT * FROM user WHERE id = ${id}
 * 处理之后的SQL语句：SELECT * FROM user WHERE id = 1 ---- Statement 处理之后的SQL语句：SELECT *
 * FROM user WHERE id = 10 ---- Statement 是否需要每次执行的时候，都要处理一次${}？------是的
 * 
 * @author 灭霸詹
 *
 */
public class DynamicSqlSource implements SqlSource {

	private MixedSqlNode rootSqlNode;

	public DynamicSqlSource(MixedSqlNode rootSqlNode) {
		this.rootSqlNode = rootSqlNode;
	}

	@Override
	public BoundSql getBoundSql(Object param) {
		DynamicContext context = new DynamicContext(param);
		// 在此处解析 SqlNode集合，合并成一条SQL语句（可能还带有#{}，但是已经处理了${}和动态标签）
		rootSqlNode.apply(context);
		System.out.println("解析#{}之前的SQL语句：" + context.getSql());
		// 针对#{}进行处理
		// 主要来处理${}中的参数名称，从入参对象中获取对应的参数值
		ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
		// 根据${ 和 }去截取字符串，最终匹配到的${}中的内容，交给TokenHandler去处理
		GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", handler);
		// 执行解析过程，返回值是处理完${}之后的值
		String sql = tokenParser.parse(context.getSql());
		System.out.println("解析#{}之前的SQL语句：" + sql);
		return new BoundSql(sql, handler.getParameterMappings());
	}

}
