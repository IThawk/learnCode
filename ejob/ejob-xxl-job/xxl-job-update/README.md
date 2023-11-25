## springboot-starter-xxl-job-demo
xxl job管理端项目demo
## xxl-job-executor-demo
xxl job执行器项目demo

## xxl-job-http
xxl job 注解自动扫描添加任务
```
xxl:
  job:
    datasource:  # xxljob 数据源配置(支持dm.pg,mysql.oracle) EnableXxlJobAutoConfiguration
      jdbc-url: jdbc:dm://x.x.x.x:5236/DAMENG
      username: xxxx
      password: xxxxx
      driverClassName: dm.jdbc.driver.DmDriver
    i18n: zh_CN
    triggerpool:
      fast:
        max: 200
      slow:
        max: 100
    logretentiondays: 30
    ###web端启动调度器
    admin:
      flag: true # 是否启动调度器 EnableXxlJobExecutorAutoConfiguration
      addresses: http://127.0.0.1:9090/xxl-job-admin #xxljobweb端的地址
    ### xxl-job executor appname
    executor:
      appname:  xxl-job-executor-test #调度器注册名称
      address:
      ip:
      port: ${random.int[9000,10000]}
      logpath:  /home/weblogic/xxl-job-admin/jobhandler 
      logretentiondays:  30
    accessToken:
    ## 自动扫描添加定时任务 EnableXxlJobHttpHandlerAutoConfiguration
    jobWebUrl: http://9.23.29.220:9090/xxl-job-admin #xxljobweb端的地址
    jobGroup: 2  #添加调度器的id
    scanPackage: com.xkcoding.orm.mybatis.plus.task #扫描包位置
    baseUrl: http://9.23.29.220:9090/  # 服务定时任务请求基础url可以理解位服务自身的基础url
    executorIp: 9.23.29.220  #执行器的Ip,就是允许调用的ip
    key:  nU1GndxnWrboVmWjwWmRoA== #添加调度器解密密钥
```

## xxl-job-http 示例
```java

import com.xxl.job.http.config.XxlJobBean;
import com.xxl.job.http.config.XxlJobMethod;

@XxlJobBean("MyTask")
public class MyTask {


    @XxlJobMethod(triggerStatus = 1)
    public String main() {
        //这个返回string 会记录到日志中
        System.out.println("333333333333333333333333333333");
        return "定时任务请求OK";
    }
}



```