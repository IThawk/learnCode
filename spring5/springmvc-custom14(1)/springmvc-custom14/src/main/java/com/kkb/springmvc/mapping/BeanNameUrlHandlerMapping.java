package com.kkb.springmvc.mapping;

import com.kkb.spring.framework.aware.BeanFactoryAware;
import com.kkb.spring.framework.factory.BeanFactory;
import com.kkb.spring.framework.factory.support.DefaultListableBeanFactory;
import com.kkb.spring.framework.ioc.BeanDefinition;
import com.kkb.springmvc.mapping.iface.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将处理器类配置到xml中的bean标签
 * @author 灭霸詹
 *
 */
public class BeanNameUrlHandlerMapping implements HandlerMapping, BeanFactoryAware {
	private DefaultListableBeanFactory beanFactory;
	/**
	 * 请求和处理器类的映射集合
	 */
	private Map<String, Object> urlHandlers = new HashMap<String, Object>();

	/**
	 * 初始化方法
	 */
	public void init(){
		List<BeanDefinition> beanDefinitions = beanFactory.getBeanDefinitions();
		for(BeanDefinition bd:beanDefinitions){
			String beanName = bd.getBeanName();
			if (beanName == null) continue;
			if (beanName.startsWith("/")){
				urlHandlers.put(beanName, beanFactory.getBean(beanName));
			}
		}

		// urlHandlers.put("/queryUser", new QueryUserHandler());
	}

	/*public BeanNameUrlHandlerMapping() {
		urlHandlers.put("/queryUser", new QueryUserHandler());
	}*/
	
	@Override
	public Object getHandler(HttpServletRequest request) throws Exception {
		String uri = request.getRequestURI();
		return urlHandlers.get(uri);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = (DefaultListableBeanFactory) beanFactory;
	}
}
