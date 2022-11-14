package com.kkb.mybatis.executor;

import java.util.List;

import com.kkb.mybatis.config.Configuration;
import com.kkb.mybatis.executor.iface.Executor;

/**
 * 处理二级缓存
 * 
 * @author 灭霸詹
 *
 */
public class CachingExecutor implements Executor {

	private Executor delegate;

	public CachingExecutor(Executor delegate) {
		this.delegate = delegate;
	}

	@Override
	public List<Object> query(String statementId, Object param, Configuration configuration) {
		// TODO 二级缓存，学员可以自行扩展
		// 当二级缓存没有找到对应的数据的话
		return delegate.query(statementId, param, configuration);
	}

}
