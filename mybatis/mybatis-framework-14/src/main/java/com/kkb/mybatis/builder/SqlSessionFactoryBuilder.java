package com.kkb.mybatis.builder;

import java.io.InputStream;
import java.io.Reader;

import org.dom4j.Document;

import com.kkb.mybatis.config.Configuration;
import com.kkb.mybatis.factory.DefaultSqlSessionFactory;
import com.kkb.mybatis.factory.SqlSessionFactory;
import com.kkb.mybatis.parser.XMLConfigBuilder;
import com.kkb.mybatis.utils.DocumentUtils;

/**
 * 作用：解析配置文件，封装Configuration，创建SqlSessionFactory
 * 
 * @author 灭霸詹
 *
 */
public class SqlSessionFactoryBuilder {

	public SqlSessionFactory build(InputStream inputStream) {
		XMLConfigBuilder configBuilder = new XMLConfigBuilder();
		Document document = DocumentUtils.createDocument(inputStream);
		Configuration configuration = configBuilder.parse(document.getRootElement());
		return build(configuration);
	}

	public SqlSessionFactory build(Reader reader) {
		return null;
	}

	private SqlSessionFactory build(Configuration configuration) {
		return new DefaultSqlSessionFactory(configuration);
	}
}
