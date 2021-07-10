package com.kaikeba.adapter;

import com.kaikeba.annotation.ResponseBody;
import com.kaikeba.conversion.IntegerTypeHandler;
import com.kaikeba.conversion.StringTypeHandler;
import com.kaikeba.conversion.TypeHandler;
import com.kaikeba.model.HandlerMethod;
import com.kaikeba.model.ModelAndView;
import com.kaikeba.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专门来处理HttpServletHandler处理器类型
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter{
    private Map<Class,TypeHandler> typeHandlers = new HashMap<>();

    public RequestMappingHandlerAdapter() {
        this.typeHandlers.put(String.class,new StringTypeHandler());
        this.typeHandlers.put(Integer.class,new IntegerTypeHandler());
        // 一次性建立所有类型的处理器
    }

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof HandlerMethod);
    }

    @Override
    public ModelAndView handleRequest(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception{
        HandlerMethod hm = (HandlerMethod) handler;
        Object controller = hm.getController();
        Method method = hm.getMethod();

        // 获取参数(参数解析)
        Object[] args = handleParameters(method,req);
        // 通过反射调用处理器方法处理请求
        Object returnValue = method.invoke(controller, args);

        // 处理返回值
        handleReturnValue(returnValue,method,resp);
        return null;
    }

    /**
     * 处理返回值（ResponseBody）
     * @param returnValue
     * @param method
     * @param resp
     */
    private void handleReturnValue(Object returnValue, Method method, HttpServletResponse resp) throws Exception{
        if (method.isAnnotationPresent(ResponseBody.class)){
            if (returnValue instanceof String){
                resp.setContentType("text/plain;charset=utf8");
                resp.getWriter().write(returnValue.toString());
            }else if(returnValue instanceof Map){
                resp.setContentType("application/json;charset=utf8");
                resp.getWriter().write(JsonUtils.object2Json(returnValue));
            }//......
        }else{
            // 视图处理
        }
    }

    /**
     * 将Request请求中的参数，绑定到处理器方法的参数中
      * @param method
     * @param req
     * @return
     * @throws Exception
     */
    private Object[] handleParameters(Method method, HttpServletRequest req) throws Exception{
        List results = new ArrayList();

        // 获取Request请求参数（Key其实对应的是Method方法参数的名称）
        Map<String, String[]> parameterMap = req.getParameterMap();

        // 获取到方法中的所有参数
        Parameter[] parameters = method.getParameters();

        for (Parameter parameter : parameters) {
            // 获取参数名称（先埋个雷）
            // 通过反编译之后，获取到的参数名称，不做特殊处理的话，name值是arg0，arg1这样的值
            String name = parameter.getName();

            // 根据参数名称获取到参数值的数组
            String[] valueArray = parameterMap.get(name);
            if (valueArray == null || valueArray.length ==0){
                results.add(null);
                continue;
            }

            // 处理参数的类型转换
            TypeHandler typeHandler = typeHandlers.get(parameter.getType());
            Object value = typeHandler.handleValue(valueArray);

            results.add(value);
        }

        return results.toArray();
    }

}
