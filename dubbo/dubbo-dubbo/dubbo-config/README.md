# dubbo-config 
## 功能描述：
    读取配置文件中的内容到spring中进行加载
## 配置文件加载
### 视图解释器 schemas
    在resource文件夹下面添加 spring.schemas 文件：添加下面内容
    http\://dubbo.apache.org/schema/dubbo/dubbo.xsd=META-INF/dubbo.xsd
    http\://code.alibabatech.com/schema/dubbo/dubbo.xsd=META-INF/compat/dubbo.xsd
    也就是xsd文件的位置
### handlers校验
    在resource文件夹下面添加 spring.handlers 文件：添加下面内容    
    http\://dubbo.apache.org/schema/dubbo=org.apache.dubbo.config.spring.schema.DubboNamespaceHandler
    http\://code.alibabatech.com/schema/dubbo=org.apache.dubbo.config.spring.schema.DubboNamespaceHandler
    定义spring启动时的handler 进行加载，
    也就是入口就是：
    org.apache.dubbo.config.spring.schema.DubboNamespaceHandler
    暴露服务配置：
    registerBeanDefinitionParser("service", new DubboBeanDefinitionParser(ServiceBean.class, true));
    引用服务配置：
    registerBeanDefinitionParser("reference", new DubboBeanDefinitionParser(ReferenceBean.class, false));
###     DubboNamespaceHandler


### org.apache.dubbo.config.spring.context.DubboLifecycleComponentApplicationListener
    继承OneTimeExecutionApplicationContextEventListener  监听spring刷新事件
### org.apache.dubbo.config.spring.context.DubboBootstrapApplicationListener
    继承OneTimeExecutionApplicationContextEventListener  监听spring刷新事件
    开启启动dubbo：
        System.out.println("启动dubbo开始发布服务");
        dubboBootstrap.start();
###  org.apache.dubbo.config.bootstrap.DubboBootstrap
        dubbo 启动类