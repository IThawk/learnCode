package com.kkb.mybatis.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.kkb.mybatis.po.User;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

/**
 * 中文名：对象图导航语言，通过它简单一致的表达式语法，可以存取对象的任意属性，调用对象的方法，遍历整个对象的结构图，实现字段类型转化等功能。
 * 它使用相同的表达式去存取对象的属性。这样可以更好的取得数据。主要是#、%和$这三个符号的使用。
 * OgnlContext用法 
 * 1.使用Ognl表达式语言取值，如果取非根元素的值，必须用#号 
 * 2.使用Ognl表达式语言取值，如果取根元素的值，不用#号
 * 3.Ognl可以调用静态方法
 */
public class OgnlDemo {

	// 非根元素
	@Test
	public void testOgnl1() throws OgnlException {
		// 创建一个Ognl上下文对象
		OgnlContext context = new OgnlContext();

		/**
		 * 1.OgnlContext放入基本变量数据
		 */
		// 放入数据
		context.put("cn", "China");
		// 获取数据（map）
		String value = (String) context.get("cn");

		System.out.println(value);

		/**
		 * 2.OgnlContext放入对象数据
		 */
		// 创建对象，设置对象属性
		User user = new User();
		user.setId(100);
		user.setUsername("Jack");
		// 【往非根元素放入数据，取值的时候表达式要用“#”】
		context.put("user", user);
		// 获取对象属性
		// 使用这种方式也可以获取
		Object s = context.get("user");
		System.out.println(s);

		// 使用Ognl表达式来获取
		// 举例：例如标签<s:a value="#user.id">取值，实际上就是运行了下面的代码获取的
		// 先构建一个Ognl表达式，再解析表达式
		Object ognl = Ognl.parseExpression("#user.id");// 构建Ognl表达式
		Object value1 = Ognl.getValue(ognl, context, context.getRoot());// 解析表达式
		System.out.println(value1);

		User user1 = new User();
		user1.setId(100);
		user1.setUsername("Jack");
		context.setRoot(user1);
		Object ognl1 = Ognl.parseExpression("id");// 构建Ognl表达式
		Object value2 = Ognl.getValue(ognl1, context, context.getRoot());// 解析表达式
		System.out.println(value2);

	}

	// 根元素，
	@Test
	public void testOgnl2() throws OgnlException {
		OgnlContext context = new OgnlContext();

		User user1 = new User();
		user1.setId(100);
		user1.setUsername("Jack");
		context.setRoot(user1);
		// 根元素直接使用id,不需要加#号
		Object ognl1 = Ognl.parseExpression("id");// 构建Ognl表达式
		Object value2 = Ognl.getValue(ognl1, context, context.getRoot());// 解析表达式
		System.out.println(value2);

	}

	// ognl对静态方法调用的支持
	@Test
	public void testOgnl3() throws Exception {
		// 创建一个Ognl上下文对象
		OgnlContext context = new OgnlContext();

		// Ognl表达式语言，调用类的静态方法
		// Object ognl = Ognl.parseExpression("@Math@floor(10.9)");
		// 由于Math类在开发中比较常用，所有也可以这样写
		Object ognl = Ognl.parseExpression("@@floor(10.9)");
		Object value = Ognl.getValue(ognl, context, context.getRoot());
		System.out.println(value);
	}

	@Test
	public void testOgnl4() throws OgnlException {
		OgnlContext context = new OgnlContext();

		User user = new User();
		user.setId(100);
		//user.setUsername("Jack");
		
		context.setRoot(user);
		
		Object ognl = Ognl.parseExpression("username != null and username != '' ");// 构建Ognl表达式
		Object value = Ognl.getValue(ognl, context, context.getRoot());// 解析表达式
		System.out.println(value);

	}
	@Test
	public void testOgnl5() throws OgnlException {
		OgnlContext context = new OgnlContext();
		
		User user = new User();
		user.setId(100);
		user.setUsername("Jack");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_parameter", user);
		
		context.setRoot(map);
		
		Object ognl = Ognl.parseExpression("_parameter.id");// 构建Ognl表达式
//		Object value = Ognl.getValue(ognl, map2, map);// 解析表达式
		Object value = Ognl.getValue(ognl, context, context.getRoot());// 解析表达式
		System.out.println(value);
		
	}

}