package com.ithawk.demo.spring.v2.formework.context;


import com.ithawk.demo.spring.v2.formework.annotation.GPAutowired;
import com.ithawk.demo.spring.v2.formework.annotation.GPController;
import com.ithawk.demo.spring.v2.formework.annotation.GPService;
import com.ithawk.demo.spring.v2.formework.aop.AopProxy;
import com.ithawk.demo.spring.v2.formework.aop.CglibAopProxy;
import com.ithawk.demo.spring.v2.formework.aop.JdkDynamicAopProxy;
import com.ithawk.demo.spring.v2.formework.aop.config.AopConfig;
import com.ithawk.demo.spring.v2.formework.aop.support.AdvisedSupport;
import com.ithawk.demo.spring.v2.formework.beans.BeanWrapper;
import com.ithawk.demo.spring.v2.formework.beans.config.BeanDefinition;
import com.ithawk.demo.spring.v2.formework.beans.config.BeanPostProcessor;
import com.ithawk.demo.spring.v2.formework.beans.support.BeanDefinitionReader;
import com.ithawk.demo.spring.v2.formework.beans.support.DefaultListableBeanFactory;
import com.ithawk.demo.spring.v2.formework.core.BeanFactory;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * IOC核心容器
 * </p>
 */
public class ApplicationContext extends DefaultListableBeanFactory implements BeanFactory {

    private static Logger logger = Logger.getLogger(ApplicationContext.class);

    private String[] configLoactions;
    private BeanDefinitionReader reader;

    //单例的IOC容器缓存
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<String, Object>();
    //通用的IOC容器
    private final Map<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<String, BeanWrapper>();

    /**
     * 创建容器的时候 可能会有多个配置
     *
     * @param configLoactions
     */
    public ApplicationContext(String... configLoactions) {
        //加载配置
        this.configLoactions = configLoactions;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 刷新容器的方法
     *
     * @throws Exception
     */
    @Override
    public void refresh() throws Exception {
        //1、定位，定位配置文件
        reader = new BeanDefinitionReader(this.configLoactions);

        //2、加载配置文件，扫描相关的类，把它们封装成BeanDefinition
        List<BeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        //3、注册，把配置信息放到容器里面(伪IOC容器)
        doRegisterBeanDefinition(beanDefinitions);

        //4、把不是延时加载的类，有提前初始化
        doAutowrited();
    }

    //只处理非延时加载的情况
    private void doAutowrited() {
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()) {
                try {
                    //
                    doGetBean(beanName);
                } catch (Exception e) {
                    logger.error("doAutowrited error: " + e.toString());
                }
            }
        }
    }

    private void doRegisterBeanDefinition(List<BeanDefinition> beanDefinitions) throws Exception {

        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())) {
                throw new Exception("The “" + beanDefinition.getFactoryBeanName() + "” is exists!!");
            }
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
        //到这里为止，容器初始化完毕
    }

    public Object getBean(Class<?> beanClass) throws Exception {
        return doGetBean(beanClass.getName());
    }

    public Object getBean(String beanName) throws Exception {
        return doGetBean(beanName);
    }

    //依赖注入，从这里开始，通过读取BeanDefinition中的信息
    //然后，通过反射机制创建一个实例并返回
    //Spring做法是，不会把最原始的对象放出去，会用一个BeanWrapper来进行一次包装
    //装饰器模式：
    //1、保留原来的OOP关系
    //2、我需要对它进行扩展，增强（为了以后AOP打基础）
    public Object doGetBean(String beanName) throws Exception {


        BeanDefinition gpBeanDefinition = this.beanDefinitionMap.get(beanName);
        //解决获取bean中出现的的使用类全名找不到的问题
        if (gpBeanDefinition == null) {
            int i = beanName.lastIndexOf('.');
            String beanNameShort = beanName.substring(i + 1);
            beanNameShort = Character.toLowerCase(beanNameShort.charAt(0)) + beanNameShort.substring(1);
            gpBeanDefinition = this.beanDefinitionMap.get(beanNameShort);
        }
        Object instance = null;

        //这个逻辑还不严谨，自己可以去参考Spring源码
        //工厂模式 + 策略模式
        BeanPostProcessor postProcessor = new BeanPostProcessor();

        postProcessor.postProcessBeforeInitialization(instance, beanName);

        //初始化bean
        instance = instantiateBean(beanName, gpBeanDefinition);

        //3、把这个对象封装到BeanWrapper中
        BeanWrapper beanWrapper = new BeanWrapper(instance);

        //4、把BeanWrapper存到IOC容器里面
//        //1、初始化

//        //class A{ B b;}
//        //class B{ A a;}
//        //先有鸡还是先有蛋的问题，一个方法是搞不定的，要分两次

        //2、拿到BeanWraoper之后，把BeanWrapper保存到IOC容器中去
        this.factoryBeanInstanceCache.put(beanName, beanWrapper);

        postProcessor.postProcessAfterInitialization(instance, beanName);

//        //3、注入
        populateBean(beanName, new BeanDefinition(), beanWrapper);


        return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }

    //自动注入属性
    private void populateBean(String beanName, BeanDefinition gpBeanDefinition, BeanWrapper gpBeanWrapper) {
        Object instance = gpBeanWrapper.getWrappedInstance();

//        gpBeanDefinition.getBeanClassName();

        Class<?> clazz = gpBeanWrapper.getWrappedClass();
        //判断只有加了注解的类，才执行依赖注入
        if (!(clazz.isAnnotationPresent(GPController.class) || clazz.isAnnotationPresent(GPService.class))) {
            return;
        }

        //获得所有的fields
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(GPAutowired.class)) {
                continue;
            }

            GPAutowired autowired = field.getAnnotation(GPAutowired.class);

            String autowiredBeanName = autowired.value().trim();
            if ("".equals(autowiredBeanName)) {
                autowiredBeanName = field.getType().getName();
            }

            //强制访问
            field.setAccessible(true);

            try {
                //为什么会为NULL，先留个坑
                if (this.factoryBeanInstanceCache.get(autowiredBeanName) == null) {
                    continue;
                }
//                if(instance == null){
//                    continue;
//                }
                field.set(instance, this.factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 初始化bean
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    private Object instantiateBean(String beanName, BeanDefinition beanDefinition) {
        //1、拿到要实例化的对象的类名
        String className = beanDefinition.getBeanClassName();

        //2、反射实例化，得到一个对象
        Object instance = null;
        try {
//            gpBeanDefinition.getFactoryBeanName()
            //假设默认就是单例,细节暂且不考虑，先把主线拉通
            if (this.factoryBeanObjectCache.containsKey(className)) {
                instance = this.factoryBeanObjectCache.get(className);
            } else {
                //实例化类
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                //执行初始化
                doInitMethod(instance, clazz);

                //aop初始化
                AdvisedSupport config = instantionAopConfig(beanDefinition);
                config.setTargetClass(clazz);
                config.setTarget(instance);

                //符合PointCut的规则的话，将代理对象
                if (config.pointCutMatch()) {
                    instance = createProxy(config).getProxy();
                }

                this.factoryBeanObjectCache.put(className, instance);
                this.factoryBeanObjectCache.put(beanDefinition.getFactoryBeanName(), instance);
            }
        } catch (Exception e) {
            logger.debug(".init no method" );
        }

        return instance;
    }

    /**
     * 执行初始化方法
     * @param instance
     * @param clazz
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void doInitMethod(Object instance, Class<?> clazz) throws IllegalAccessException, InvocationTargetException {
        //调用初始化init
        try {
            Method method = clazz.getDeclaredMethod("init");
            if (method != null) {
                method.invoke(instance);
            }
        }catch (NoSuchMethodException e){
            logger.debug(clazz.getName() +".init no method" );
        }

    }

    private AopProxy createProxy(AdvisedSupport config) {

        Class targetClass = config.getTargetClass();
        /**
         * 如果是接口 使用JDK动态代理
         */
        if (targetClass.getInterfaces().length > 0) {
            return new JdkDynamicAopProxy(config);
        }
        return new CglibAopProxy(config);
    }

    /**
     * 初始化AOP配置
     *
     * @param gpBeanDefinition
     * @return
     */
    private AdvisedSupport instantionAopConfig(BeanDefinition gpBeanDefinition) {
        AopConfig config = new AopConfig();
        config.setPointCut(this.reader.getConfig().getProperty("pointCut"));
        config.setAspectClass(this.reader.getConfig().getProperty("aspectClass"));
        config.setAspectBefore(this.reader.getConfig().getProperty("aspectBefore"));
        config.setAspectAfter(this.reader.getConfig().getProperty("aspectAfter"));
        config.setAspectAfterThrow(this.reader.getConfig().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(this.reader.getConfig().getProperty("aspectAfterThrowingName"));
        return new AdvisedSupport(config);
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    public Properties getConfig() {
        return this.reader.getConfig();
    }
}
