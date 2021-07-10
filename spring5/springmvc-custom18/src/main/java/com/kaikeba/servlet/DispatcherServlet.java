package com.kaikeba.servlet;

import com.kaikeba.adapter.HandlerAdapter;
import com.kaikeba.mapping.HandlerMapping;
import com.kkb.spring.factory.support.DefaultListableBeanFactory;
import com.kkb.spring.reader.XmlBeanDefinitionReader;
import com.kkb.spring.resource.ClasspathResource;
import com.kkb.spring.resource.Resource;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 只需要搞一个Servlet用来分发所有请求
 * 前端控制器
 */
public class DispatcherServlet extends AbstractServlet{

    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    private List<HandlerMapping> handlerMappings = new ArrayList<>();

    private DefaultListableBeanFactory beanFactory;

    private static final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";
    /**
     * Servlet规范中的初始化方法
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {

        String location = config.getInitParameter(CONTEXT_CONFIG_LOCATION);

        // 初始化spring容器
        initSpringContainer(location);

        // 初始化策略集合
        initStrategies();
    }

    /**
     * 初始化策略集合
     */
    private void initStrategies() {
        initHandlerMappings();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        // 可以根据类型进行一次性初始化所有的子类型的实例
        handlerAdapters = beanFactory.getBeansByType(HandlerAdapter.class);
//        handlerAdapters.add(new HttpServletHandlerAdapter()) ;
//        handlerAdapters.add(new SimpleControllerHandlerAdapter());
    }

    private void initHandlerMappings() {
        handlerMappings = beanFactory.getBeansByType(HandlerMapping.class);

//        handlerMappings.add(new BeanNameUrlHandlerMapping());
//        handlerMappings.add(new SimpleUrlHandlerMapping());
    }

    private void initSpringContainer(String location) {
        beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        Resource resource = new ClasspathResource(location);
        beanDefinitionReader.loadBeanDefinitions(resource);

        // 针对容器内说有的单例bean，可以一次性进行对象创建

    }

    @Override
    public void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 1、根据请求查找对应的【处理器】进行处理（处理器是什么？如何查找处理器？）
            Object handler = getHandler(req);
            if (handler == null){
                return;
            }

            // 2、根据处理器查找对应的适配器
            // 适配器（DispatcherServlet-- 适配器 -->handler）
            // 适配器和处理器是一对一的
            HandlerAdapter ha = getHandlerAdapter(handler);
            if (ha == null){
                return;
            }

            // 执行处理器的处理逻辑，并返回处理结果 （通过处理器适配器去执行处理器）
            ha.handleRequest(handler,req,resp);
            /*if (handler instanceof HttpServletHandler){
                ((HttpServletHandler)handler).handleRequest(req,resp);
            }
*/
            // 将处理结果通过response响应给客户端
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // 暗号：詹哥会弹琴

    private HandlerAdapter getHandlerAdapter(Object handler) {
        // 这就是策略模式的玩法，替换掉很多if语句进行判断的方式
        if (handlerAdapters != null){
            for (HandlerAdapter ha : handlerAdapters) {
                // 需要根据处理器类型去判断哪个适配器和它适配
                if (ha.supports(handler)) {
                    // 返回对应的适配器
                    return ha;
                }
            }
        }
        return null;
    }

    private Object getHandler(HttpServletRequest req) {

        //根据处理器和请求的映射关系进行查找（映射关系可能存储在xml配置文件的标签中，可能存储到map集合中）
        // 方式1：BeanNameUrlHandlerMapping
        // <bean name="/queryUser" class="处理器类的全路径"/>

        // 方式2：SimpleUrlHandlerMapping
        // <bean class="专门用来建立映射关系的类">
        //  <props>
        //    <prop key="请求URL">处理器类的全路径</prop>
        //  </props>
        // </bean>


        // 先去方式1中查找
        // 如果找不到，则继续找
        // 再去方式2中查找
        // 如果找不到，则继续找

        if(handlerMappings != null){
            for (HandlerMapping hm : handlerMappings) {
                Object handler = hm.getHandler(req);
                if (handler!=null){
                    return handler;
                }
            }
        }

        return null;
    }
}
