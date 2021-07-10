package com.kkb.mybatis.test;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.kkb.mybatis.framework.config.Configuration;
import com.kkb.mybatis.framework.config.MappedStatement;
import com.kkb.mybatis.framework.sqlnode.iface.SqlNode;
import com.kkb.mybatis.framework.sqlsource.iface.SqlSource;
import com.kkb.mybatis.po.User;
import com.kkb.mybatis.framework.sqlnode.IfSqlNode;
import com.kkb.mybatis.framework.sqlnode.MixedSqlNode;
import com.kkb.mybatis.framework.sqlnode.StaticTextSqlNode;
import com.kkb.mybatis.framework.sqlnode.TextSqlNode;
import com.kkb.mybatis.framework.sqlsource.BoundSql;
import com.kkb.mybatis.framework.sqlsource.DynamicSqlSource;
import com.kkb.mybatis.framework.sqlsource.ParameterMapping;
import com.kkb.mybatis.framework.sqlsource.RawSqlSource;

/**
 * 1.properties配置文件升级为XML配置文件 2.使用面向过程思维去优化代码
 * 
 * @author 灭霸詹
 *
 */
public class MybatisV2 {

	/**
	 * properties文件存储的集合对象
	 */
	private Configuration configuration = new Configuration();
	private String namespace;
	// 如果包含${}或者动态标签，那么isDynamic为true
	private boolean isDynamic = false;

	@Test
	public void test() {
		loadConfiguration();
		// 查询用户信息（需要数据库中有一张用户表）
		// List<User> users = selectUserList("queryUserById", "王五");
		// List<User> users = selectUserList("queryUserById", 1);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", "王五");
		params.put("id", 1);
		// params.put("sex", "男");
		List<User> users = selectList("test.findUserById", params);
		System.out.println(users);
	}

	/**
	 * 加载xml文件
	 */
	private void loadConfiguration() {
		String location = "mybatis-config.xml";
		InputStream inputStream = getResourceAsStream(location);
		Document document = createDocument(inputStream);
		// 按照mybatis的语义，对XML中的内容进行解析
		parserConfiguration(document.getRootElement());
	}

	private void parserConfiguration(Element rootElement) {
		Element envirsElement = rootElement.element("environments");
		parseEnvironments(envirsElement);
		Element mappersElement = rootElement.element("mappers");
		parseMappers(mappersElement);

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
			parseMapper(mapperElement);
		}
	}

	private void parseMapper(Element mapperElement) {
		String resource = mapperElement.attributeValue("resource");

		InputStream inputStream = getResourceAsStream(resource);
		Document document = createDocument(inputStream);
		// 按照映射文件的语义进行解析
		parseXmlMapper(document.getRootElement());
	}

	@SuppressWarnings("unchecked")
	private void parseXmlMapper(Element rootElement) {
		// 为了方便管理statement，需要使用statement唯一标识
		namespace = rootElement.attributeValue("namespace");

		List<Element> selectElements = rootElement.elements("select");
		for (Element selectElement : selectElements) {
			parseStatementElement(selectElement);
		}
	}

	private void parseStatementElement(Element selectElement) {
		String statementId = selectElement.attributeValue("id");

		if (statementId == null || statementId.equals("")) {
			return;
		}
		// 一个CURD标签对应一个MappedStatement对象
		// 一个MappedStatement对象由一个statementId来标识，所以保证唯一性
		// statementId = namespace + "." + CRUD标签的id属性
		statementId = namespace + "." + statementId;

		String parameterType = selectElement.attributeValue("parameterType");
		Class<?> parameterClass = resolveType(parameterType);

		String resultType = selectElement.attributeValue("resultType");
		Class<?> resultClass = resolveType(resultType);

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
		SqlSource sqlSource = parseScriptNode(selectElement);
		return sqlSource;
	}

	/**
	 * 处理select等标签下的SQL脚本
	 * 
	 * @param selectElement
	 * @return
	 */
	private SqlSource parseScriptNode(Element selectElement) {

		// 解析SQL脚本信息
		MixedSqlNode rootSqlNode = parseDynamicTags(selectElement);

		// 将sql脚本信息需要封装SqlSource对象
		SqlSource sqlSource = null;
		// 思考？封装到哪个SqlSource对象中呢？
		if (isDynamic) {
			sqlSource = new DynamicSqlSource(rootSqlNode);
		} else {
			sqlSource = new RawSqlSource(rootSqlNode);
		}

		return sqlSource;
	}

	private MixedSqlNode parseDynamicTags(Element selectElement) {

		List<SqlNode> sqlNodes = new ArrayList<>();

		// 获取select标签的子节点总数
		int nodeCount = selectElement.nodeCount();
		// 遍历所有子节点
		for (int i = 0; i < nodeCount; i++) {
			Node node = selectElement.node(i);
			// 判断子节点是文本节点还是元素节点
			if (node instanceof Text) {
				// 获取文本信息
				String sqlText = node.getText();
				if (sqlText == null || "".equals(sqlText)) {
					continue;
				}
				// 将sql文本封装到TextSqlNode
				TextSqlNode textSqlNode = new TextSqlNode(sqlText);
				// 如果包含${}
				if (textSqlNode.isDynamic()) {
					sqlNodes.add(textSqlNode);
					isDynamic = true;
				} else {
					sqlNodes.add(new StaticTextSqlNode(sqlText));
				}
			} else if (node instanceof Element) {
				Element element = (Element) node;
				String elementName = element.getName();

				if (elementName.equals("if")) {

					String test = element.attributeValue("test");
					// 递归封装SqlNode数据
					MixedSqlNode mixedSqlNode = parseDynamicTags(element);

					// 封装IfSqlNode数据
					IfSqlNode ifSqlNode = new IfSqlNode(test, mixedSqlNode);
					sqlNodes.add(ifSqlNode);
				} else if (elementName.equals("where")) {
					// ...
				} // .....
				isDynamic = true;
			}
		}
		return new MixedSqlNode(sqlNodes);
	}

	private Class<?> resolveType(String parameterType) {
		try {
			Class<?> clazz = Class.forName(parameterType);
			return clazz;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Document createDocument(InputStream inputStream) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(inputStream);
			return document;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	private InputStream getResourceAsStream(String location) {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(location);
		return inputStream;
	}

	/**
	 * 改造成通用的程序方法
	 * 
	 * @param statementId
	 * @param param
	 * @return
	 */
	private <T> List<T> selectList(String statementId, Object param) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		List<T> results = new ArrayList<T>();
		try {
			connection = getConnection();

			// 定义sql语句 ?表示占位符
			// 先要获取MappedStatement
			MappedStatement mappedStatement = configuration.getMappedStatementById(statementId);
			// 再获取MappedStatement中的SqlSource
			SqlSource sqlSource = mappedStatement.getSqlSource();
			// 通过SqlSource的API处理，获取BoundSql
			// TODO 还没有处理
			BoundSql boundSql = sqlSource.getBoundSql(param);
			// 通过BoundSql获取到SQL语句
			String sql = boundSql.getSql();

			// 获取预处理 statement
			preparedStatement = connection.prepareStatement(sql);

			// 设置参数，第一个参数为 sql 语句中参数的序号（从 1 开始），第二个参数为设置的
			// 参数处理
			setParameters(preparedStatement, param, boundSql);

			// 向数据库发出 sql 执行查询，查询出结果集
			rs = preparedStatement.executeQuery();

			// 遍历查询结果集
			// 将每一行映射为一个User对象，每一列作为User对象的属性进行设置
			handleResultSet(rs, results, mappedStatement);

			return results;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private <T> void handleResultSet(ResultSet rs, List<T> results, MappedStatement mappedStatement) throws Exception {
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

			results.add((T) result);
		}
	}

	@SuppressWarnings("rawtypes")
	private void setParameters(PreparedStatement preparedStatement, Object param, BoundSql boundSql) throws Exception {
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

	/**
	 * 通过数据源获取连接
	 * 
	 * @return
	 */
	private Connection getConnection() {
		try {
			DataSource dataSource = configuration.getDataSource();
			Connection connection = dataSource.getConnection();
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}