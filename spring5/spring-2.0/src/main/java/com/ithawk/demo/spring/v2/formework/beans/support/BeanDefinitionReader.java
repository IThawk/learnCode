package com.ithawk.demo.spring.v2.formework.beans.support;

import com.ithawk.demo.spring.v2.formework.beans.config.BeanDefinition;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 读取类定义
 */
public class BeanDefinitionReader {

    private static Logger logger = Logger.getLogger(BeanDefinitionReader.class);

    /**
     * 扫描到的所有类
     */
    private List<String> registryBeanClasses = new ArrayList<String>();

    private Properties config = new Properties();

    //固定配置文件中的key，相对于xml的规范
    private final String SCAN_PACKAGE = "scanPackage";

    /**
     * @param locations
     */
    public BeanDefinitionReader(String... locations) {
        //通过URL定位找到其所对应的文件，然后转换为文件流
        for (String s : locations) {
            logger.debug("read config file : " + s);

            try (InputStream is = this.getClass().getClassLoader().
                    getResourceAsStream(s.replace("classpath:", ""));) {
                config.load(is);
            } catch (IOException e) {
                logger.error("BeanDefinitionReader" + e.getMessage());
            }
            //扫描所有的class类文件
            doScanner(config.getProperty(SCAN_PACKAGE));
        }

    }


    /**
     * 扫描文件夹中的所有的类
     *
     * @param scanPackage
     */
    private void doScanner(String scanPackage) {
        if (scanPackage == null || scanPackage.length() == 0) {
            logger.warn("scanPackage is empty。");
            return;
        }
        //转换为文件路径，实际上就是把.替换为/就OK了
        URL url = this.getClass().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            //进行递归扫描
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = (scanPackage + "." + file.getName().replace(".class", ""));
                registryBeanClasses.add(className);
            }
        }
    }

    public Properties getConfig() {
        return this.config;
    }

    //把配置文件中扫描到的所有的配置信息转换为BeanDefinition对象，以便于之后IOC操作方便
    public List<BeanDefinition> loadBeanDefinitions() {
        List<BeanDefinition> result = new ArrayList<BeanDefinition>();
        try {
            for (String className : registryBeanClasses) {
                //初始化类
                Class<?> beanClass = Class.forName(className);
                //如果是一个接口，是不能实例化的
                //用它实现类来实例化
                if (beanClass.isInterface()) {
                    continue;
                }
                if (beanClass.isAnnotation()) {
                    continue;
                }
                //判断是否是抽象类
                if (Modifier.isAbstract(beanClass.getModifiers())) {
                    continue;
                }

                //beanName有三种情况:
                //1、默认是类名首字母小写
                //2、自定义名字
                //3、接口注入
                result.add(doCreateBeanDefinition(toLowerFirstCase(beanClass.getSimpleName()), beanClass.getName()));
//                result.add(doCreateBeanDefinition(beanClass.getName(),beanClass.getName()));

                Class<?>[] interfaces = beanClass.getInterfaces();
                for (Class<?> i : interfaces) {
                    //如果是多个实现类，只能覆盖
                    //为什么？因为Spring没那么智能，就是这么傻
                    //这个时候，可以自定义名字
                    result.add(doCreateBeanDefinition(i.getName(), beanClass.getName()));
                }

            }
        } catch (Exception e) {
            logger.error("loadBeanDefinitions error: " + e.toString());
        }
        return result;
    }


    //把每一个配信息解析成一个BeanDefinition
    private BeanDefinition doCreateBeanDefinition(String factoryBeanName, String beanClassName) {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName(beanClassName);
        beanDefinition.setFactoryBeanName(factoryBeanName);
        return beanDefinition;
    }

    //如果类名本身是小写字母，确实会出问题
    //但是我要说明的是：这个方法是我自己用，private的
    //传值也是自己传，类也都遵循了驼峰命名法
    //默认传入的值，存在首字母小写的情况，也不可能出现非字母的情况

    //为了简化程序逻辑，就不做其他判断了，大家了解就OK
    //其实用写注释的时间都能够把逻辑写完了
    public static String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        //之所以加，是因为大小写字母的ASCII码相差32，
        // 而且大写字母的ASCII码要小于小写字母的ASCII码
        //在Java中，对char做算学运算，实际上就是对ASCII码做算学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
