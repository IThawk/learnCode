## SpringBoot的读取自定义配置问题

## maven引入依赖

```java
package com.ithawk.demo.springboot.ex.controller;


        import com.ithawk.demo.springboot.ex.dto.Country;
        import com.ithawk.demo.springboot.ex.dto.School;
        import com.ithawk.demo.springboot.ex.dto.Student;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.context.annotation.PropertySource;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RestController;

        @RestController
        @PropertySource(value="custom.properties", encoding = "utf-8")
        public class SomeController {

        @Value("${server.port}")
        private int port;

        @Value("${student.name}")
        private String name;

        @Autowired
        private Student student;

        @Autowired
        private Country country;

        @Autowired
        private School school;


        @GetMapping("/port")
        public String portHandle() {
        return "服务器端口号为：" + port;
        }

        @GetMapping("/name")
        public String nameHandle() {
        return name;
        }

        @GetMapping("/stu")
        public Object studentHandle() {
        return student;
        }

        @GetMapping("/country")
        public Object couHandle() {
        return country;
        }

        @GetMapping("/school")
        public Object schoolHandle() {
        return school;
        }


        }

```

### 配置信息
    配置文件位置：resources/custom.properties
```properties
student.name=张三
student.age=23
student.score=93.5

country.cities[0]=北京
country.cities[1]=上海
country.cities[2]=广州

school.departs[0].name=软件学院
school.departs[0].address=1号楼
school.departs[0].tel=1000000

school.departs[1].name=计算机学院
school.departs[1].address=2号楼
school.departs[1].tel=2000000

school.departs[2].name=美术学院
school.departs[2].address=3号楼
school.departs[2].tel=3000000
```

### 请求

    http://127.0.0.1:7777/ooo/base/info

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