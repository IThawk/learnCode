package cn.com.search.configurer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import cn.com.search.core.Result;

public class ReturnHandler implements HandlerMethodReturnValueHandler {

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return returnType.getParameterType() == Result.class;
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		if(returnValue instanceof Result) {
			Result result = (Result)returnValue;
			mavContainer.setRequestHandled(true);
	        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
	        response.setContentType("application/json;charset=UTF-8");
	        response.getWriter().append(result.toString()).flush();
		}
	}

}
