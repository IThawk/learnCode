package com.kkb.mybatis.framework.sqlnode.iface;

import com.kkb.mybatis.framework.sqlnode.support.DynamicContext;

/**
 * 提供对封装的SqlNode信息进行解析处理
 * 
 * @author 灭霸詹
 *
 */
public interface SqlNode {

	/**
	 * 节点处理
	 * 
	 * @param context
	 *            节点处理时需要的上下文信息
	 */
	void apply(DynamicContext context);
}
