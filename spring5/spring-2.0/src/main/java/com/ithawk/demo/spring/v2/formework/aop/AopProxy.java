package com.ithawk.demo.spring.v2.formework.aop;

/**
 *
 * 一、原理区别：
 *
 * java动态代理是利用反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理。
 *
 * 而cglib动态代理是利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。
 *
 * 1、如果目标对象实现了接口，默认情况下会采用JDK的动态代理实现AOP
 * 2、如果目标对象实现了接口，可以强制使用CGLIB实现AOP
 *
 * 3、如果目标对象没有实现了接口，必须采用CGLIB库，spring会自动在JDK动态代理和CGLIB之间转换
 *
 * 如何强制使用CGLIB实现AOP？
 *  （1）添加CGLIB库，SPRING_HOME/cglib/*.jar
 *  （2）在spring配置文件中加入<aop:aspectj-autoproxy proxy-target-class="true"/>
 *
 * JDK动态代理和CGLIB字节码生成的区别？
 *  （1）JDK动态代理只能对实现了接口的类生成代理，而不能针对类
 *  （2）CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法
 *    因为是继承，所以该类或方法最好不要声明成final
 */
public interface AopProxy {


    Object getProxy();


    Object getProxy(ClassLoader classLoader);
}
