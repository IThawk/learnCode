## dubbo服务发布的过程

### 1：发布服务入口的一个监听事件 spring的事件：

```java
import org.springframework.context.ApplicationListener;
//dubbo 与spring的入口：本质spring的事件通知
import org.apache.dubbo.config.spring.context.OneTimeExecutionApplicationContextEventListener;
//DubboBootstrapApplicationListener继承OneTimeExecutionApplicationContextEventListener
//重新onApplicationContextEvent方法，然后针对ContextRefreshedEvent事件
//onContextRefreshedEvent进行dubbo服务启动
import org.apache.dubbo.config.spring.context.DubboBootstrapApplicationListener.onApplicationContextEvent;

public class DubboBootstrapApplicationListener {
    private void onContextRefreshedEvent(ContextRefreshedEvent event) {
        System.out.println("启动dubbo开始发布服务");
        dubboBootstrap.start();
    }
}

```

### 2：dubbo的启动流程

#### 1：启动类 org.apache.dubbo.config.bootstrap.DubboBootstrap

```java
import org.apache.dubbo.config.bootstrap.DubboBootstrap.start;
//服务发布 调用
import org.apache.dubbo.config.bootstrap.DubboBootstrap.exportServices;
//根据服务配置进行发布
import org.apache.dubbo.config.ServiceConfig.export;

import org.apache.dubbo.config.ServiceConfig.doExport;

import org.apache.dubbo.config.ServiceConfig.doExportUrls;
//根据对应的配置
import org.apache.dubbo.config.ServiceConfig.doExportUrlsFor1Protocol;

public class ServiceConfig {
    public void doExportUrlsFor1Protocol() {
        logger.info("开始发布！");
        Exporter<?> exporter = PROTOCOL.export(wrapperInvoker);
        exporters.add(exporter);
    }

}
//
```

#### 2:根据不同的注册中心进行服务注册（zk）

```java
//注册中心发布服务方法

import org.apache.dubbo.registry.integration.RegistryProtocol.export;

import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol.export;
//注册中心注册服务
import org.apache.dubbo.registry.integration.RegistryProtocol.register;
//进行实现的注册方法    
import org.apache.dubbo.registry.support.FailbackRegistry.register;
//实现zk进行注册    
import org.apache.dubbo.registry.zookeeper.ZookeeperRegistry.doRegister;
import org.apache.dubbo.remoting.zookeeper.support.AbstractZookeeperClient.create;
```

#### 2:发布节点创建消息并且启动服务

```java
import org.apache.dubbo.registry.support.FailbackRegistry.subscribe;
//启动dubbo服务

import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol.openServer;

import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol.createServer;

public class DubboProtocol {
    private void createServer(URL url) {
        //根据SPI获取到对应的server进行启动服务
        ExchangeServer server;
        try {
            server = Exchangers.bind(url, requestHandler);
        } catch (RemotingException e) {
            throw new RpcException("Fail to start server(url: " + url + ") " + e.getMessage(), e);
        }

    }
}

```

    org.apache.dubbo.remoting.exchange.Exchangers.getExchanger(java.lang.String)
    ```java
       public static Exchanger getExchanger(String type) {
            return ExtensionLoader.getExtensionLoader(Exchanger.class).getExtension(type);
        }
    ```
    在remote-api 定义dubbo的扩展点
    META-INF/dubbo/internal/org.apache.dubbo.remoting.exchange.Exchanger:
    header=org.apache.dubbo.remoting.exchange.support.header.HeaderExchanger
    
    ```java
    
      @Override
        public ExchangeServer bind(URL url, ExchangeHandler handler) throws RemotingException {
            return new HeaderExchangeServer(Transporters.bind(url, new DecodeHandler(new HeaderExchangeHandler(handler))));
        }
    ```
    
    org.apache.dubbo.remoting.Transporters.bind(org.apache.dubbo.common.URL, org.apache.dubbo.remoting.ChannelHandler...)
    
    ```java
        public static Transporter getTransporter() {
            System.out.println("获取发布的服务的版本。。。");
            return ExtensionLoader.getExtensionLoader(Transporter.class).getAdaptiveExtension();
        }
    
    ```
    dubbo-remoting\dubbo-remoting-netty\src\main\resources\META-INF\dubbo\internal\org.apache.dubbo.remoting.Transporter
    
    
    
    org.apache.dubbo.remoting.transport.netty4.NettyTransporter
    
    
    org.apache.dubbo.remoting.transport.AbstractServer.AbstractServer
    
    
    org.apache.dubbo.remoting.transport.netty4.NettyServer.doOpen

# 源码阅读

## org.apache.dubbo.config.ServiceConfig 服务发布代码入口

* 例子
    * private static final Protocol PROTOCOL = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension()
      ;
* 获取扩展点： org.apache.dubbo.common.extension.ExtensionLoader.getExtension(java.lang.String)
* 获取激活扩展点
    * org.apache.dubbo.common.extension.ExtensionLoader.getActivateExtension(org.apache.dubbo.common.URL,
      java.lang.String, java.lang.String)
* 获取自适应扩展点（自适应类，自适应方法，包装类）：
    * org.apache.dubbo.common.extension.ExtensionLoader.getAdaptiveExtension

## org.apache.dubbo.common.extension.ExtensionLoader

    org.apache.dubbo.common.extension.ExtensionLoader.loadClass
    org.apache.dubbo.common.extension.LoadingStrategy  这个是定义spi扫描文件的位置
    实现类如下： 
    org.apache.dubbo.common.extension.DubboInternalLoadingStrategy   Integer.MIN_VALUE
    org.apache.dubbo.common.extension.DubboExternalLoadingStrategy   Integer.MIN_VALUE +1
    org.apache.dubbo.common.extension.DubboLoadingStrategy    org.apache.dubbo.common.lang.Prioritized.NORMAL_PRIORITY 0
    org.apache.dubbo.common.extension.ServicesLoadingStrategy   Integer.MAX_VALUE

## javassist 动态编译

```
javassist使用全解析
Java 字节码以二进制的形式存储在 .class 文件中，每一个 .class 文件包含一个 Java 类或接口。
Javaassist 就是一个用来 处理 Java 字节码的类库。它可以在一个已经编译好的类中添加新的方法，
或者是修改已有的方法，并且不需要对字节码方面有深入的了解。同时也可以去生成一个新的类对象，通过完全手动的方式。
http://www.javassist.org/tutorial/tutorial2.html
```

* org.apache.dubbo.common.compiler.support.AbstractCompiler
* org.apache.dubbo.common.compiler.support.JavassistCompiler.doCompile

## spring.handlers文件 定义spring处理配置

* org.apache.dubbo.config.spring.schema.DubboNamespaceHandler

## 服务发布

* registerBeanDefinitionParser("service", new DubboBeanDefinitionParser(ServiceBean.class, true));
* org.apache.dubbo.config.spring.schema.DubboBeanDefinitionParser.DubboBeanDefinitionParser

* org.apache.dubbo.config.spring.context.DubboBootstrapApplicationListener
* org.apache.dubbo.config.bootstrap.DubboBootstrap.start
* org.apache.dubbo.config.bootstrap.DubboBootstrap.exportServices
* org.apache.dubbo.config.ServiceConfig.export
* org.apache.dubbo.config.ServiceConfig.doExport
* org.apache.dubbo.config.ServiceConfig.doExportUrlsFor1Protocol
* org.apache.dubbo.registry.integration.RegistryProtocol.export
* org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol.export
* org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol.createServer
* org.apache.dubbo.remoting.exchange.support.header.HeaderExchanger.bind
* org.apache.dubbo.remoting.transport.netty4.NettyTransporter.bind
* org.apache.dubbo.rpc.protocol.ProtocolListenerWrapper.export
* org.apache.dubbo.qos.protocol.QosProtocolWrapper.export

### 服务注册的步骤

* org.apache.dubbo.registry.support.FailbackRegistry.register
* org.apache.dubbo.registry.zookeeper.ZookeeperRegistry.doRegister (服务注册)

## 服务订阅：

* org.springframework.beans.factory.FactoryBean.getObject
* org.apache.dubbo.config.spring.ReferenceBean.getObject
* org.apache.dubbo.config.ReferenceConfig.init
* org.apache.dubbo.config.ReferenceConfig.createProxy
* org.apache.dubbo.rpc.ProxyFactory$Adaptive.getProxy(org.apache.dubbo.rpc.Invoker, boolean)
* org.apache.dubbo.rpc.proxy.javassist.JavassistProxyFactory.getProxy
* org.apache.dubbo.rpc.proxy.InvokerInvocationHandler    调用的方式的地方
  org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker.invoke
* org.apache.dubbo.rpc.cluster.support.FailfastCluster
## IOC 容器中获取属性

    org.apache.dubbo.common.extension.ExtensionLoader.getExtension(java.lang.String, boolean)
    org.apache.dubbo.common.extension.ExtensionLoader.injectExtension
    org.apache.dubbo.common.extension.ExtensionFactory
    org.apache.dubbo.config.spring.extension.SpringExtensionFactory

## AOP

    org.apache.dubbo.common.extension.ExtensionLoader.getExtension(java.lang.String, boolean)
    org.apache.dubbo.common.extension.ExtensionLoader.createExtension

