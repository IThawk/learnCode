# 书写springboot加载的start的例子
* 定义加载类
    * 定义加载
        * 在resources/META-INF/spring.factories定义加载类的入口
        * org.springframework.boot.autoconfigure.EnableAutoConfiguration
        ```
         @EnableXxlJobAutoConfiguration()
         //添加 去除 /xxl-job-admin/api/registry   
      /xxl-job-admin/api/registryRemove   /xxl-job-admin/api/callback 
       权限控制
        ```
