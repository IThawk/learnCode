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
public class DubboBootstrapApplicationListener{
    private void onContextRefreshedEvent(ContextRefreshedEvent event) {
        System.out.println("启动dubbo开始发布服务");
        dubboBootstrap.start();
    }
}

```
### 2：dubbo的启动流程
#### 1：启动类  org.apache.dubbo.config.bootstrap.DubboBootstrap
```java
import org.apache.dubbo.config.bootstrap.DubboBootstrap.start;
//服务发布 调用
import org.apache.dubbo.config.bootstrap.DubboBootstrap.exportServices;
//根据服务配置进行发布
import    org.apache.dubbo.config.ServiceConfig.export;
    
import    org.apache.dubbo.config.ServiceConfig.doExport;
    
import    org.apache.dubbo.config.ServiceConfig.doExportUrls;
//根据对应的配置
import   org.apache.dubbo.config.ServiceConfig.doExportUrlsFor1Protocol;
public class ServiceConfig{
    public void doExportUrlsFor1Protocol(){
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
    
import    org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol.export;
//注册中心注册服务
import    org.apache.dubbo.registry.integration.RegistryProtocol.register;
//进行实现的注册方法    
import    org.apache.dubbo.registry.support.FailbackRegistry.register;
//实现zk进行注册    
import    org.apache.dubbo.registry.zookeeper.ZookeeperRegistry.doRegister;
import org.apache.dubbo.remoting.zookeeper.support.AbstractZookeeperClient.create;
```
#### 2:发布节点创建消息并且启动服务
```java
import     org.apache.dubbo.registry.support.FailbackRegistry.subscribe;
//启动dubbo服务

import     org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol.openServer;
    
import    org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol.createServer;

public class DubboProtocol{
 private void createServer(URL url) {
        //根据SPI获取到对应的server进行启动服务
        ExchangeServer server;
        try {
            server = Exchangers.bind(url, requestHandler);
        }catch (RemotingException e) {
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