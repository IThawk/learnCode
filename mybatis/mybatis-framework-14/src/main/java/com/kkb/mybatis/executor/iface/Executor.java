package com.kkb.mybatis.executor.iface;

import java.util.List;

import com.kkb.mybatis.config.Configuration;

/**
 * 执行器接口，提供执行JDBC代码的功能
 * @author 灭霸詹
 *
 */
public interface Executor {

	List<Object> query(String statementId, Object param, Configuration configuration);
}
