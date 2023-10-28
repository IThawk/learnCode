package com.ithawk.demo.springboot.starter.autoconfiguration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@MapperScan(basePackages = "com.xxx.xxx.mapper.dispatchDB", sqlSessionFactoryRef = "factoryOne")
public class DataBaseOneConfig {
    /**
     @Primary 表示主数据源，未指定使用哪个数据源时默认使用
     @ConfigurationProperties 表示从配置文件中获取值， prefix 表示前缀匹配
     */
    /**
     * 注入 数据源1
     */
    @Bean(name = "DataSourceOne")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource getDateSourceOne() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration globalConfiguration() {
        return new org.apache.ibatis.session.Configuration();
    }

//    @Bean(name = "DataSourcePropertiesOne")
//    @ConfigurationProperties(prefix = "spring.datasource.db1")
//    public DataSourceProperties db2DataSourceProperties(){
//        return new DataSourceProperties();
//    }

    /**
     * 注入 mybatis 的 sqlSessionFactory
     * 设置 数据源 与 xml 路径
     */
    @Bean(name = "factoryOne")
    @Primary
    public SqlSessionFactory sqlSessionFactoryOne(@Qualifier("DataSourceOne") DataSource datasource,
                                                  org.apache.ibatis.session.Configuration configuration) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        // 设置数据源
        bean.setDataSource(datasource);
        bean.setConfiguration(configuration);
        // 设置 mybatis 的 xml 所在位置
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis-mapper/*Mapper.xml"));
        return bean.getObject();
    }

}
