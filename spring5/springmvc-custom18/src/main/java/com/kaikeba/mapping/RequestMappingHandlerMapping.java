package com.kaikeba.mapping;

import com.kaikeba.annotation.Controller;
import com.kaikeba.annotation.RequestMapping;
import com.kaikeba.model.HandlerMethod;
import com.ithawk.demo.spring.custom.aware.BeanFactoryAware;
import com.ithawk.demo.spring.custom.factory.BeanFactory;
import com.ithawk.demo.spring.custom.factory.support.DefaultListableBeanFactory;
import com.ithawk.demo.spring.custom.init.InitializingBean;
import com.ithawk.demo.spring.custom.ioc.BeanDefinition;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestMappingHandlerMapping implements HandlerMapping, BeanFactoryAware, InitializingBean {
    private Map<String, HandlerMethod> urlHandlers = new HashMap<>();

    private DefaultListableBeanFactory beanFactory;

    @Override
    public Object getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri == null || "".equals(uri)){
            return null;
        }
        return this.urlHandlers.get(uri);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public void afterPropertiesSet() {
        List<BeanDefinition> beanDefinitions = beanFactory.getBeanDefinitions();
        for (BeanDefinition bd : beanDefinitions) {
            // 获取对应的bean的类型
            Class<?> clazzType = bd.getClazzType();
            // 是否是带有@Controller或者@RequestMapping注解的类
            if (isHandler(clazzType)){
                Method[] methods = clazzType.getMethods();
                // 继续遍历
                for (Method method : methods) {
                    // 只有带有@RequestMapping注解的方法，才是我们要解析的方法
                    if (method.isAnnotationPresent(RequestMapping.class)){

                        // 将类上和方法上的url进行合并
                        String url = combine(clazzType,method);
                        // 建立URL和HandlerMethod的映射关系
                        HandlerMethod hm = new HandlerMethod(beanFactory.getBean(bd.getBeanName()),method);

                        this.urlHandlers.put(url,hm);
                    }
                }
            }
        }

    }

    private String combine(Class<?> clazzType, Method method) {
        RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
        String methodUrl = methodRequestMapping.value();

        RequestMapping clazzRequestMapping = clazzType.getAnnotation(RequestMapping.class);
        String clazzUrl = null;
        if (clazzRequestMapping != null){
            // 获取类上RequestMapping注解中的url
            clazzUrl = clazzRequestMapping.value();
        }

        StringBuffer sb = new StringBuffer();
        if (clazzUrl != null && !clazzUrl.equals("")){
            if (!clazzUrl.startsWith("/")){
                sb.append("/");
            }
            sb.append(clazzUrl);
        }

        if (!methodUrl.startsWith("/")){
            sb.append("/");
        }
        sb.append(methodUrl);
        return sb.toString();
    }

    private boolean isHandler(Class handler){
        return (handler.isAnnotationPresent(Controller.class)
                || handler.isAnnotationPresent(RequestMapping.class));
    }
}
