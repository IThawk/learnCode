package com.kkb.mybatis.sqlnode;

import com.kkb.mybatis.sqlnode.iface.SqlNode;
import com.kkb.mybatis.utils.GenericTokenParser;
import com.kkb.mybatis.utils.OgnlUtils;
import com.kkb.mybatis.utils.SimpleTypeRegistry;
import com.kkb.mybatis.utils.TokenHandler;

/**
 * 封装带有${}的SQL文本信息
 * 
 * @author 灭霸詹
 *
 */
public class TextSqlNode implements SqlNode {

	private String sqlText;

	public TextSqlNode(String sqlText) {
		this.sqlText = sqlText;
	}

	public boolean isDynamic() {
		if (sqlText.indexOf("${") > -1) {
			return true;
		}
		return false;
	}

	@Override
	public void apply(DynamicContext context) {
		// 主要来处理${}中的参数名称，从入参对象中获取对应的参数值
		TokenHandler handler = new BindingTokenHandler(context);
		// 根据${ 和 }去截取字符串，最终匹配到的${}中的内容，交给TokenHandler去处理
		GenericTokenParser tokenParser = new GenericTokenParser("${", "}", handler);
		// 执行解析过程，返回值是处理完${}之后的值
		sqlText = tokenParser.parse(sqlText);
		context.appendSql(sqlText);
	}

	class BindingTokenHandler implements TokenHandler {

		private DynamicContext context;

		public BindingTokenHandler(DynamicContext context) {
			this.context = context;
		}

		@Override
		public String handleToken(String content) {
			Object parameterObject = context.getBindings().get("_parameter");
			if (parameterObject == null) {
				return "";
			} else if (SimpleTypeRegistry.isSimpleType(parameterObject.getClass())) {
				return parameterObject.toString();
			}

			Object value = OgnlUtils.getValue(content, parameterObject);
			return value == null ? "" : value.toString();
		}

	}
}
