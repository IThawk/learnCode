package com.kkb.mybatis.framework.sqlnode;

import java.util.ArrayList;
import java.util.List;

import com.kkb.mybatis.framework.sqlnode.iface.SqlNode;
import com.kkb.mybatis.framework.sqlnode.support.DynamicContext;

/**
 * 封装平级的SqlNode集合信息
 * 
 * @author 灭霸詹
 *
 */
public class MixedSqlNode implements SqlNode {

	private List<SqlNode> sqlNodes = new ArrayList<SqlNode>();

	public MixedSqlNode(List<SqlNode> sqlNodes) {
		this.sqlNodes = sqlNodes;
	}

	@Override
	public void apply(DynamicContext context) {
		for (SqlNode sqlNode : sqlNodes) {
			sqlNode.apply(context);
		}
	}

}
