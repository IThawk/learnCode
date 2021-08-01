## SpringBoot的Actuator监控器

    Actuator是Spring Boot提供的一个可插拔模块，用于对工程进行监控。其通过不同的监控终端实现不同的监控功能。Spring Boot的Actuator可以部署在每个工程中，实现对每个工程的监控。

## maven引入依赖

```xml
     <!-- actuator依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### 配置信息

```yaml
management:
  endpoints:
    web:
      exposure:
        include: '*'   # 开启所有监控终端
        #exclude: env,info # 排除
      base-path: /base   # 指定监控器的基本路径，一般不改，均采用默认路径/actuator
  server:
    port: 8888   # 指定监控器的端口号
    servlet:
      context-path: /ooo  # 指定监控器的上下文路径

# 自定义info信息
info:
  author:
    name: 张三
    age: 23
  company:
    name: 中国海洋xxx公司
    address: http://www.xxx.com
  app:
    group-id: @groupId@
    artifact-id: @artifactId@
```

### 请求
    http://127.0.0.1:7777/ooo/base/info  查询info信息
    http://127.0.0.1:7777/ooo/base    查询所有的接口
    http://127.0.0.1:9999/xxx/some   WEB端接口

```json
{
  "author": {
    "name": "张三",
    "age": 23
  },
  "company": {
    "name": "中国海洋xxx公司",
    "address": "http://www.xxx.com"
  },
  "app": {
    "group-id": "com.ithawk.demo",
    "artifact-id": "01-springboot-actuator",
    "version": "1.0-SNAPSH"
  }
}
```
### 测试类
```java
package com.ithawk.demo.springboot.ex;


import com.ithawk.demo.springboot.ex.service.SomeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {

    @Autowired
    private SomeService service;

    /**
     * web接口测试
     */
    @Autowired
    private MockMvc mvc;

    @Test
    public void contextLoads() {
        service.doSome();
    }


    @Test
    public void getList() throws Exception {
        // 测试状态码
        mvc.perform(MockMvcRequestBuilders.get("/some"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

```
### 问题解决

#### springboot yml配置文件使用@project.xxxx@ 启动报错Do not use @ for indentation

```xml

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <version>2.4.1</version>
        </plugin>

        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-resources-plugin</artifactId>
            <configuration>
                <delimiters>@</delimiters>
                <useDefaultDelimiters>false</useDefaultDelimiters>
            </configuration>
        </plugin>
    </plugins>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <filtering>true</filtering>
        </resource>
    </resources>
</build>
```