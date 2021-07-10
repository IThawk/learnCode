package com.kkb.mybatis.parser;

import org.dom4j.Element;

import com.kkb.mybatis.config.Configuration;
import com.kkb.mybatis.config.MappedStatement;
import com.kkb.mybatis.sqlsource.iface.SqlSource;
import com.kkb.mybatis.utils.ReflectUtils;

/**
 * 专门用来解析CRUD标签的
 * 
 * @author 灭霸詹
 *
 */
public class XMLStatementBuilder {
	private Configuration configuration;

	public XMLStatementBuilder(Configuration configuration) {
		this.configuration = configuration;
	}

	public void parse(Element selectElement, String namespace) {
		String statementId = selectElement.attributeValue("id");

		if (statementId == null || statementId.equals("")) {
			return;
		}
		// 一个CURD标签对应一个MappedStatement对象
		// 一个MappedStatement对象由一个statementId来标识，所以保证唯一性
		// statementId = namespace + "." + CRUD标签的id属性
		statementId = namespace + "." + statementId;

		String parameterType = selectElement.attributeValue("parameterType");
		Class<?> parameterClass = ReflectUtils.resolveType(parameterType);

		String resultType = selectElement.attributeValue("resultType");
		Class<?> resultClass = ReflectUtils.resolveType(resultType);

		String statementType = selectElement.attributeValue("statementType");
		statementType = statementType == null || statementType == "" ? "prepared" : statementType;

		// SqlSource的封装过程
		SqlSource sqlSource = createSqlSource(selectElement);

		// TODO 建议使用构建者模式去优化
		MappedStatement mappedStatement = new MappedStatement(statementId, parameterClass, resultClass, statementType,
				sqlSource);
		configuration.addMappedStatement(statementId, mappedStatement);

	}

	private SqlSource createSqlSource(Element selectElement) {
		XMLScriptBuilder scriptBuilder = new XMLScriptBuilder();
		
		SqlSource sqlSource = scriptBuilder.parseScriptNode(selectElement);
		return sqlSource;
	}

}
