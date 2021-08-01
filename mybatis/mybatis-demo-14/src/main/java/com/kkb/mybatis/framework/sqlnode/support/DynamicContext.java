package com.kkb.mybatis.framework.sqlnode.support;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装SqlNode解析过程中需要的上下文数据
 * 
 * @author 灭霸詹
 *
 */
public class DynamicContext {
	// 拼接SQL语句
	private StringBuffer sb = new StringBuffer();

	// 封装解析SqlNode节点时需要的一些参数信息
	private Map<String, Object> bindings = new HashMap<>();

	public DynamicContext(Object param) {
		this.bindings.put("_parameter", param);
	}

	public void appendSql(String sqlText) {
		this.sb.append(sqlText);
		this.sb.append(" ");
	}

	public String getSql() {
		return sb.toString();
	}

	public Map<String, Object> getBindings() {
		return bindings;
	}

}
