package com.kkb.mybatis.test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.kkb.mybatis.builder.SqlSessionFactoryBuilder;
import com.kkb.mybatis.factory.SqlSessionFactory;
import com.kkb.mybatis.io.Resources;
import com.kkb.mybatis.po.User;
import com.kkb.mybatis.sqlsession.SqlSession;

/**
 * 1.以面向对象的思维去改造mybatis手写框架 2.将手写的mybatis的代码封装一个通用的框架（java工程）给程序员使用
 * 
 * @author 灭霸詹
 *
 */
public class MybatisV3 {

	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void before() {
		String location = "mybatis-config.xml";
		InputStream inputStream = Resources.getResource(location);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	@Test
	public void test() {

		// 查询用户信息（需要数据库中有一张用户表）
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", "王五");
		params.put("id", 1);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<User> users = sqlSession.selectList("test.findUserById", params);
		System.out.println(users);
	}
}