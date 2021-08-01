package com.kkb.mybatis.sqlsource;

/**
 * 主要用来存储解析#{}时获取到的参数名称和参数类型
 * 
 * @author 灭霸詹
 *
 */
public class ParameterMapping {

	// #{}中的参数名称
	private String name;

	// 该参数对应的类型
	private Class<?> type;

	public ParameterMapping(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

}
