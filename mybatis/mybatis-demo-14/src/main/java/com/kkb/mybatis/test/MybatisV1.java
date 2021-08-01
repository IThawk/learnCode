package com.kkb.mybatis.test;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import com.kkb.mybatis.po.User;

/**
 * 解决硬编码问题
 * 
 * @author 灭霸詹
 *
 */
public class MybatisV1 {

	/**
	 * properties文件存储的集合对象
	 */
	private Properties properties = new Properties();

	/**
	 * 加载properties文件
	 */
	private void loadProperties() {
		try {
			String name = "jdbc.properties";
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(name);
			properties.load(inputStream);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Test
	public void test() {
		loadProperties();
		// 查询用户信息（需要数据库中有一张用户表）
		// List<User> users = selectUserList("queryUserById", "王五");
		// List<User> users = selectUserList("queryUserById", 1);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", "王五");
		params.put("sex", "男");
		List<User> users = selectList("queryUserById", params);
		System.out.println(users);
	}

	/**
	 * 改造成通用的程序方法
	 * 
	 * @param statementId
	 * @param param
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> List<T> selectList(String statementId, Object param) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		List<T> results = new ArrayList<T>();
		try {
			// 加载数据库驱动
			Class.forName(properties.getProperty("db.driver"));

			// 通过驱动管理类获取数据库链接connection = DriverManager
			connection = DriverManager.getConnection(properties.getProperty("db.url"),
					properties.getProperty("db.username"), properties.getProperty("db.password"));

			// 定义sql语句 ?表示占位符
			String sql = properties.getProperty("db.sql." + statementId);

			// 获取预处理 statement
			preparedStatement = connection.prepareStatement(sql);

			// 设置参数，第一个参数为 sql 语句中参数的序号（从 1 开始），第二个参数为设置的
			// 参数处理
			if (SimpleTypeRegistry.isSimpleType(param.getClass())) {
				// 只要参数类型是符合这个条件的，那么说明入参就是一个基本类型的参数或者String类型的参数
				preparedStatement.setObject(1, param);
			} else if (param instanceof Map) {
				Map map = (Map) param;

				String params = properties.getProperty("db.sql." + statementId + ".params");
				String[] paramArray = params.split(",");
				for (int i = 0; i < paramArray.length; i++) {
					String paramName = paramArray[i];
					Object value = map.get(paramName);
					preparedStatement.setObject(i + 1, value);
				}

				/*
				 * Set keySet = ((Map) param).keySet(); for (Object object : keySet) {
				 * 
				 * preparedStatement.setObject(1, param); }
				 */
				// ???????

			} else {
				// ....
			}

			// 向数据库发出 sql 执行查询，查询出结果集
			rs = preparedStatement.executeQuery();

			// 遍历查询结果集
			// 将每一行映射为一个User对象，每一列作为User对象的属性进行设置
			String resultclassname = properties.getProperty("db.sql." + statementId + ".resultclassname");
			Class<?> resultType = Class.forName(resultclassname);
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

	// private List<User> selectUserList(String statementId, Object param) {
	// Connection connection = null;
	// PreparedStatement preparedStatement = null;
	// ResultSet rs = null;
	//
	// List<User> users = new ArrayList<User>();
	// try {
	// // 加载数据库驱动
	// Class.forName(properties.getProperty("db.driver"));
	//
	// // 通过驱动管理类获取数据库链接connection = DriverManager
	// connection = DriverManager.getConnection(properties.getProperty("db.url"),
	// properties.getProperty("db.username"),
	// properties.getProperty("db.password"));
	//
	// // 定义sql语句 ?表示占位符
	// String sql = properties.getProperty("db.sql." + statementId);
	//
	// // 获取预处理 statement
	// preparedStatement = connection.prepareStatement(sql);
	//
	// // 设置参数，第一个参数为 sql 语句中参数的序号（从 1 开始），第二个参数为设置的
	// // 参数处理
	// preparedStatement.setObject(1, param);
	//
	// // 向数据库发出 sql 执行查询，查询出结果集
	// rs = preparedStatement.executeQuery();
	//
	// // 遍历查询结果集
	// // 将每一行映射为一个User对象，每一列作为User对象的属性进行设置
	// String resultclassname = properties.getProperty("db.sql." + statementId +
	// ".resultclassname");
	// Class<?> resultType = Class.forName(resultclassname);
	// while (rs.next()) {
	// // 结果处理
	// Object user = resultType.newInstance();
	//
	// ResultSetMetaData metaData = rs.getMetaData();
	// // 获取结果集每一行有多少列
	// int columnCount = metaData.getColumnCount();
	//
	// for (int i = 1; i <= columnCount; i++) {
	// // 获取列的名称
	// String columnName = metaData.getColumnName(i);
	// // 要求：SELECT中显示的列名要和类中的属性名完全一致
	// Field field = resultType.getDeclaredField(columnName);
	// field.setAccessible(true);
	// field.set(user, rs.getObject(columnName));
	// }
	//
	// users.add((User) user);
	// }
	//
	// return users;
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// // 释放资源
	// if (rs != null) {
	// try {
	// rs.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	// if (preparedStatement != null) {
	// try {
	// preparedStatement.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	// if (connection != null) {
	// try {
	// connection.close();
	// } catch (SQLException e) {
	// }
	// }
	// }
	// return null;
	// }
}
