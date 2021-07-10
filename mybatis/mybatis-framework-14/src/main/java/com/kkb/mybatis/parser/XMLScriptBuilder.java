package com.kkb.mybatis.parser;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

import com.kkb.mybatis.sqlnode.IfSqlNode;
import com.kkb.mybatis.sqlnode.MixedSqlNode;
import com.kkb.mybatis.sqlnode.StaticTextSqlNode;
import com.kkb.mybatis.sqlnode.TextSqlNode;
import com.kkb.mybatis.sqlnode.iface.SqlNode;
import com.kkb.mybatis.sqlsource.DynamicSqlSource;
import com.kkb.mybatis.sqlsource.RawSqlSource;
import com.kkb.mybatis.sqlsource.iface.SqlSource;

/**
 * 专门用来解析SQL脚本信息的，最终封装成SqlSource对象
 * 
 * @author 灭霸詹
 *
 */
public class XMLScriptBuilder {

	private boolean isDynamic = false;
	
	public SqlSource parseScriptNode(Element selectElement ) {
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

}
