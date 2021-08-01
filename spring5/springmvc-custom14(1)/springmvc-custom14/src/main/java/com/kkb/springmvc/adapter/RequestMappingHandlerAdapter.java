package com.kkb.springmvc.adapter;

import com.kkb.springmvc.adapter.iface.HandlerAdapter;
import com.kkb.springmvc.annotation.ResponseBody;
import com.kkb.springmvc.model.HandlerMethod;
import com.kkb.springmvc.model.ModelAndView;
import com.kkb.springmvc.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 适配并处理注解方式的处理器类的
 * @author 灭霸詹
 *
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {

	@Override
	public boolean supports(Object handler) {
		return (handler instanceof HandlerMethod);
	}

	@Override
	public ModelAndView handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HandlerMethod hm = (HandlerMethod) handler;
		Object controller = hm.getController();
		Method method = hm.getMethod();

		// 处理参数
		Object[] params = invokeParams(request,method);

		// 调用Controller类中的方法
		Object returnValue = method.invoke(controller, params);

		// 处理返回值
		handleReturnValue(returnValue,response,method);

		return null;
	}

	private void handleReturnValue(Object returnValue, HttpServletResponse response, Method method) throws  Exception{
		if (method.isAnnotationPresent(ResponseBody.class)){
			if (returnValue instanceof  String){
				response.setContentType("text/plain;charset=utf-8");
				response.getWriter().write(returnValue.toString());
			}else if (returnValue instanceof  Map){
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(JsonUtils.object2Json(returnValue));
			}else{
				//TODO
			}
		}else{
			// 视图显示
		}
	}

	private Object[] invokeParams(HttpServletRequest request, Method method) {
		List<Object> params = new ArrayList<>();

		// 获取请求中的所有参数
		Map<String, String[]> parameterMap = request.getParameterMap();
		// 获取Method中的参数
		Parameter[] parameters = method.getParameters();
		for (Parameter parameter : parameters) {
			// 此时获取到的参数名称，如果不经过特殊处理，那么获取到的是arg0.arg1这样的参数名称
			String name = parameter.getName();
			// 要求：方法参数的名称和请求URL中的key要一致
			String[] strings = parameterMap.get(name);
			if (strings == null || strings.length == 0) continue;

			Class<?> type = parameter.getType();
			handleType(type,strings,params);
		}
		return  params.toArray();
	}

	/**
	 * 根据参数类型，分别去处理不同类型的参数
	 * @param type
	 * @param strings
	 * @param params
	 */
	private void handleType(Class<?> type, String[] strings, List<Object> params) {
		if (type == Integer.class){
			params.add(Integer.parseInt(strings[0]));
		}else if (type == String.class){
			params.add(strings[0]);
		}else{
			// TODO
		}
	}

}
