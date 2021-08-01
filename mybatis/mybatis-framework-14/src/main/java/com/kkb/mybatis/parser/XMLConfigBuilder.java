package com.kkb.mybatis.parser;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import com.kkb.mybatis.config.Configuration;
import com.kkb.mybatis.io.Resources;
import com.kkb.mybatis.utils.DocumentUtils;

/**
 * 主要是用来解析全局配置文件的
 * 
 * @author 灭霸詹
 *
 */
public class XMLConfigBuilder {

	private Configuration configuration;

	public XMLConfigBuilder() {
		this.configuration = new Configuration();
	}

	public Configuration parse(Element rootElement) {
		Element envirsElement = rootElement.element("environments");
		parseEnvironments(envirsElement);
		Element mappersElement = rootElement.element("mappers");
		parseMappers(mappersElement);
		return configuration;
	}

	@SuppressWarnings("unchecked")
	private void parseEnvironments(Element envirsElement) {
		String defaultId = envirsElement.attributeValue("default");
		List<Element> envElements = envirsElement.elements("environment");
		for (Element element : envElements) {
			String id = element.attributeValue("id");
			if (id.equals(defaultId)) {
				parseDataSource(element);
			}
		}
	}

	/**
	 * 
	 * @param element
	 *            <environment>
	 */
	private void parseDataSource(Element element) {
		Element dataSourceElement = element.element("dataSource");
		String type = dataSourceElement.attributeValue("type");
		if (type.equals("DBCP")) {
			BasicDataSource dataSource = new BasicDataSource();

			Properties properties = parseProperty(dataSourceElement);

			dataSource.setDriverClassName(properties.getProperty("driver"));
			dataSource.setUrl(properties.getProperty("url"));
			dataSource.setUsername(properties.getProperty("username"));
			dataSource.setPassword(properties.getProperty("password"));

			configuration.setDataSource(dataSource);
		}
	}

	@SuppressWarnings("unchecked")
	private Properties parseProperty(Element dataSourceElement) {
		Properties properties = new Properties();

		List<Element> propElements = dataSourceElement.elements("property");
		for (Element propElement : propElements) {
			String name = propElement.attributeValue("name");
			String value = propElement.attributeValue("value");
			properties.put(name, value);
		}
		return properties;
	}

	/**
	 * 
	 * @param mappers
	 *            <mappers>
	 */
	@SuppressWarnings("unchecked")
	private void parseMappers(Element mappers) {
		List<Element> mapperElements = mappers.elements("mapper");
		for (Element mapperElement : mapperElements) {
			String resource = mapperElement.attributeValue("resource");

			InputStream inputStream = Resources.getResource(resource);
			Document document = DocumentUtils.createDocument(inputStream);
			// 按照映射文件的语义进行解析
			XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(configuration);
			mapperBuilder.parse(document.getRootElement());
		}
	}

}
