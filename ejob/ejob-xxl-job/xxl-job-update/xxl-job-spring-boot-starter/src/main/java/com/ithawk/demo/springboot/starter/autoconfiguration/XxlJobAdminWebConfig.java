package com.ithawk.demo.springboot.starter.autoconfiguration;

import com.ithawk.demo.springboot.starter.autoconfiguration.jobhandler.*;
import com.xxl.job.core.config.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@EnableConfigurationProperties(XxlJobProperties.class)
@ComponentScan(basePackages = {"com.xxl.job"})
@Component
@MapperScan(basePackages = "com.xxl.job.admin.dao", sqlSessionFactoryRef = "xxlJobSqlFactory")
public class XxlJobAdminWebConfig {


    /**
     @Primary 表示主数据源，未指定使用哪个数据源时默认使用
     @ConfigurationProperties 表示从配置文件中获取值， prefix 表示前缀匹配
     */
    /**
     * 注入 数据源2
     */
    @ConditionalOnClass(DataSourceBuilder.class)
    @Bean(name = "xxlJobDataSource")
    @ConfigurationProperties(prefix = "xxl.job.datasource")
    public DataSource getDateSourceTwo() {
        return DataSourceBuilder.create().build();
    }

    /**
     @Primary 表示主数据源，未指定使用哪个数据源时默认使用
     @ConfigurationProperties 表示从配置文件中获取值， prefix 表示前缀匹配
     */
    /**
     * 注入 数据源2
     */
    @ConditionalOnMissingClass("org.springframework.boot.jdbc.DataSourceBuilder")
    @Bean(name = "xxlJobDataSource")
    @ConfigurationProperties(prefix = "xxl.job.old")
    @ConditionalOnProperty(prefix = "xxl.job.datasource", name = "old", havingValue = "true")
    public DataSource getDateSourceTwoT() {
        return XxlJobDataSourceBuilder.create().build();
    }

    /**
     * 注入 mybatis 的 sqlSessionFactory
     * 设置 数据源 与 xml 路径
     */
    @Bean(name = "xxlJobSqlFactory")
    public SqlSessionFactory sqlSessionFactoryOne(@Qualifier("xxlJobDataSource") DataSource datasource, XxlJobProperties xxlJobProperties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        // 设置数据源
        bean.setDataSource(datasource);

        if (String.valueOf(xxlJobProperties.getDatasource().get("driver-class-name")).contains("postgresql") ||
                String.valueOf(xxlJobProperties.getDatasource().get("driverClassName")).contains("postgresql")) {
            // 设置 mybatis 的 xml 所在位置
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/xxl-job-mybatis-mapper/pg/*Mapper.xml"));

        } else if (String.valueOf(xxlJobProperties.getDatasource().get("driver-class-name")).contains("mysql") ||
                String.valueOf(xxlJobProperties.getDatasource().get("driverClassName")).contains("mysql")) {
            // 设置 mybatis 的 xml 所在位置
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/xxl-job-mybatis-mapper/mysql/*Mapper.xml"));

        } else if (String.valueOf(xxlJobProperties.getDatasource().get("driver-class-name")).contains("DmDriver") ||
                String.valueOf(xxlJobProperties.getDatasource().get("driverClassName")).contains("DmDriver")) {
            // 设置 mybatis 的 xml 所在位置
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/xxl-job-mybatis-mapper/dm/*Mapper.xml"));

        } else if (String.valueOf(xxlJobProperties.getDatasource().get("driver-class-name")).contains("OracleDriver") ||
                String.valueOf(xxlJobProperties.getDatasource().get("driverClassName")).contains("OracleDriver")) {
            // 设置 mybatis 的 xml 所在位置
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/xxl-job-mybatis-mapper/oracle/*Mapper.xml"));

        }else if (String.valueOf(xxlJobProperties.getDatasource().get("driver-class-name")).contains("org.h2.Driver") ||
                String.valueOf(xxlJobProperties.getDatasource().get("driverClassName")).contains("org.h2.Driver")) {
            // 设置 mybatis 的 xml 所在位置
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/xxl-job-mybatis-mapper/h2/*Mapper.xml"));

        }
        return bean.getObject();
    }

    /*这个配置是用于开启调度器配置*/
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
