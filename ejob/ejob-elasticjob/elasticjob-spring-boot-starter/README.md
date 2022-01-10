# elasticjob-spring-boot-starter
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![Release Version](https://img.shields.io/badge/release-0.1.0-red.svg)](https://github.com/TFdream/elasticjob-spring-boot-starter/releases) [![Build Status](https://travis-ci.org/TFdream/elasticjob-spring-boot-starter.svg?branch=master)](https://travis-ci.org/TFdream/elasticjob-spring-boot-starter)

## Overview
Elastic-Job-Lite Spring Boot Starter.

## Quick Start
### 1. Add elasticjob-spring-boot-starter dependency
pom.xml:
```
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.12.RELEASE</version>
    </parent>

    <properties>
        <spring-boot.version>1.5.12.RELEASE</spring-boot.version>
        <slf4j.version>1.7.25</slf4j.version>
        <java.version>1.7</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- Spring Boot -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </dependency>

        <!-- elasticjob -->
        <dependency>
            <groupId>io.dreamstudio</groupId>
            <artifactId>elasticjob-spring-boot-starter</artifactId>
            <version>0.1.0</version>
        </dependency>
    </dependencies>
```

### 2. application.yml
job reg config:
```
elasticjob:
  regCenter:
    server-lists: localhost:2181
    namespace: demo/elasticjob
```

### 3. Define job
#### 3.1 SimpleJob
```
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import io.dreamstudio.elasticjob.annotation.JobScheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JobScheduled(jobName = "demoSimpleJob", cron = "0 0/1 * * * ?", shardingTotalCount = 1)
public class DemoSimpleJob implements SimpleJob {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("SimpleJob execute");
    }
}
```

#### 3.2 DataflowJob
```
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import io.dreamstudio.elasticjob.annotation.JobScheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@JobScheduled(jobName = "demoDataFlowJob", cron = "0 0/1 * * * ?", shardingTotalCount = 1)
public class DemoDataFlowJob implements DataflowJob<String> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        logger.info("fetchData execute");
        List<String> jobList = new ArrayList<>();
        for(int i=0; i<10; i++) {
            jobList.add("job"+i);
        }
        return jobList;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> data) {
        logger.info("processData execute");
    }
}
```

### 4. Start Application
```
import io.dreamstudio.elasticjob.annotation.EnableElasticJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableElasticJob
public class JobApp {

    public static void main(String[] args) {
        SpringApplication.run(JobApp.class, args);
    }
}
```


