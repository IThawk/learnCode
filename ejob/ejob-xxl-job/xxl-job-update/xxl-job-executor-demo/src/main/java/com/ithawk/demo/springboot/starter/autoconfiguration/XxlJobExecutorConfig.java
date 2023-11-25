package com.ithawk.demo.springboot.starter.autoconfiguration;

import com.ithawk.demo.springboot.starter.autoconfiguration.jobhandler.*;
import com.xxl.job.core.config.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@EnableConfigurationProperties(XxlJobProperties.class)
@Component
public class XxlJobExecutorConfig {

    @Value("${xxl.job.admin.addresses:\"\"}")
    private String adminAddresses;

    @Value("${xxl.job.accessToken:\"\"}")
    private String accessToken;

    @Value("${xxl.job.executor.appname:\"\"}")
    private String appname;

    @Value("${xxl.job.executor.address:\"\"}")
    private String address;

    @Value("${xxl.job.executor.ip:\"\"}")
    private String ip;

    @Value("${xxl.job.executor.port:8080}")
    private int port;

    @Value("${xxl.job.executor.logpath:\"\"}")
    private String logPath;

    @Value("${xxl.job.executor.logretentiondays:10}")
    private int logRetentionDays;

    @Bean
    @ConditionalOnProperty(prefix = "xxl.job.admin", name = "flag", havingValue = "true")
    public XxlJobSpringExecutor xxlJobExecutor() {

        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAppname(appname);
        xxlJobSpringExecutor.setAddress(address);
        xxlJobSpringExecutor.setIp(ip);
        xxlJobSpringExecutor.setPort(port);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath(logPath);
        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);

        return xxlJobSpringExecutor;
    }

    @Bean
    @ConditionalOnProperty(prefix = "xxl.job.admin", name = "flag", havingValue = "true")
    public CommandJobHandler commandJobHandler() {
        CommandJobHandler xxlJobSpringExecutor = new CommandJobHandler();
        return xxlJobSpringExecutor;
    }


    @Bean
    @ConditionalOnProperty(prefix = "xxl.job.admin", name = "flag", havingValue = "true")
    public HttpJobHandler httpJobHandler() {

        HttpJobHandler xxlJobSpringExecutor = new HttpJobHandler();
        return xxlJobSpringExecutor;
    }


    @Bean
    @ConditionalOnProperty(prefix = "xxl.job.admin", name = "flag", havingValue = "true")
    public LifeCycleJobHandler lifeCycleJobHandler() {
        LifeCycleJobHandler xxlJobSpringExecutor = new LifeCycleJobHandler();
        return xxlJobSpringExecutor;
    }


    @Bean
    @ConditionalOnProperty(prefix = "xxl.job.admin", name = "flag", havingValue = "true")
    public ShardingJobHandler shardingJobHandler() {
        ShardingJobHandler xxlJobSpringExecutor = new ShardingJobHandler();
        return xxlJobSpringExecutor;
    }

    @Bean
    @ConditionalOnProperty(prefix = "xxl.job.admin", name = "flag", havingValue = "true")
    public SimpleJobHandler simpleJobHandler() {
        SimpleJobHandler xxlJobSpringExecutor = new SimpleJobHandler();
        return xxlJobSpringExecutor;
    }
}
