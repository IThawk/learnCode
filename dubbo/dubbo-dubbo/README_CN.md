## dubbo服务发布的过程
    发布服务入口的一个监听事件
    org.apache.dubbo.config.spring.context.OneTimeExecutionApplicationContextEventListener
    
    org.apache.dubbo.config.spring.context.DubboBootstrapApplicationListener.onApplicationContextEvent
    
    org.apache.dubbo.config.spring.context.DubboBootstrapApplicationListener.onContextRefreshedEvent
    
    org.apache.dubbo.config.bootstrap.DubboBootstrap.start
    
    org.apache.dubbo.config.bootstrap.DubboBootstrap.exportServices
    
    org.apache.dubbo.config.ServiceConfig.export
    
    org.apache.dubbo.config.ServiceConfig.doExport
    
    org.apache.dubbo.config.ServiceConfig.doExportUrls
    
    org.apache.dubbo.config.ServiceConfig.doExportUrlsFor1Protocol
    
    ```java
    
     logger.info("开始发布！");
                            Exporter<?> exporter = PROTOCOL.export(wrapperInvoker);
                            exporters.add(exporter);
    ```
    
    
    org.apache.dubbo.registry.integration.RegistryProtocol.export
    
    
    org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol.export
    
    org.apache.dubbo.registry.integration.RegistryProtocol.register
    
    org.apache.dubbo.registry.support.FailbackRegistry.register
    
    org.apache.dubbo.registry.zookeeper.ZookeeperRegistry.doRegister
    
    org.apache.dubbo.remoting.zookeeper.support.AbstractZookeeperClient.create(java.lang.String, boolean)


    org.apache.dubbo.registry.support.FailbackRegistry.subscribe

    org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol.openServer
    
    org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol.createServer
    
    ```java
    
      //根据SPI获取到对应的server进行启动服务
            ExchangeServer server;
            try {
                server = Exchangers.bind(url, requestHandler);
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