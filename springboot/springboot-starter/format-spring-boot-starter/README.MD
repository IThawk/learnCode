# 书写springboot加载的start的例子
* 定义加载类
    * 定义加载
        * 在resources/META-INF/spring.factories定义加载类的入口
        * org.springframework.boot.autoconfigure.EnableAutoConfiguration
        ```
            @Import(FormatAutoConfiguration.class)
            @EnableConfigurationProperties(HelloProperties.class)
            @Configuration
      
            @Import:引入加载其他配置
            @EnableConfigurationProperties：使用激活配置属性类
        ```
