package com.kaikeba.mapping;

import com.ithawk.demo.spring.custom.aware.BeanFactoryAware;
import com.ithawk.demo.spring.custom.factory.BeanFactory;
import com.ithawk.demo.spring.custom.factory.support.DefaultListableBeanFactory;
import com.ithawk.demo.spring.custom.init.InitializingBean;
import com.ithawk.demo.spring.custom.ioc.BeanDefinition;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <bean name="/queryUser" class="com.kaikeba.handler.QueryUserHandler" />
 */
public class BeanNameUrlHandlerMapping implements HandlerMapping, BeanFactoryAware, InitializingBean {
    private Map<String,Object> urlHandlers = new HashMap<>();

    // 思考：BeanFactory如何注入
    private DefaultListableBeanFactory beanFactory;

    public BeanNameUrlHandlerMapping() {
        // 方式1：BeanNameUrlHandlerMapping
        // <bean name="/queryUser" class="处理器类的全路径"/>
        //TODO
//        this.urlHandlers.put("/queryUser",new QueryUserHandler());

        // 构造方法中是否可以用到BeanFactory实例？？？？是先new还是先初始化
    }

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

    /**
     * 初始化方法
     */
    @Override
    public void afterPropertiesSet() {
        List<BeanDefinition> beanDefinitions = beanFactory.getBeanDefinitions();
        for (BeanDefinition bd : beanDefinitions) {
            String beanName = bd.getBeanName();
            if (beanName.startsWith("/")){
                // 建立了url和bean实例的映射关系
                this.urlHandlers.put(beanName , beanFactory.getBean(beanName));
            }
        }
    }
}
