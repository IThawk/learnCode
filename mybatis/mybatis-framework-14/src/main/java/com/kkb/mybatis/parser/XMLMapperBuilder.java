package com.kkb.mybatis.parser;

import java.util.List;

import org.dom4j.Element;

import com.kkb.mybatis.config.Configuration;

/**
 * 解析映射文件
 * 
 * @author 灭霸詹
 *
 */
public class XMLMapperBuilder {
	private Configuration configuration;

	public XMLMapperBuilder(Configuration configuration) {
		this.configuration = configuration;
	}

	@SuppressWarnings("unchecked")
	public void parse(Element rootElement) {
		//TODO 映射文件中，不只是拥有CRUD标签，还有sql片段，还有resultMap等标签的解析
		
		// 为了方便管理statement，需要使用statement唯一标识
		String namespace = rootElement.attributeValue("namespace");

		List<Element> selectElements = rootElement.elements("select");
		for (Element selectElement : selectElements) {
			XMLStatementBuilder statementBuilder = new XMLStatementBuilder(configuration);
			statementBuilder.parse(selectElement, namespace);
		}

	}

}
