package com.xkcoding.orm.mybatis.plus.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * <p>
 * mybatis-plus 配置
 * </p>
 *
 * @package: com.xkcoding.orm.mybatis.plus.config
 * @description: mybatis-plus 配置
 * @author: yangkai.shen
 * @date: Created in 2018/11/8 17:29
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
@MapperScan(basePackages = {"com.xkcoding.orm.mybatis.plus.mapper"},sqlSessionFactoryRef="sqlSessionFactory")
@EnableTransactionManagement
public class MybatisPlusConfig {

    /**
     @Primary 表示主数据源，未指定使用哪个数据源时默认使用
     @ConfigurationProperties 表示从配置文件中获取值， prefix 表示前缀匹配
     */
    /**
     * 注入 数据源1
     */
    @Bean(name = "DataSourceOne")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDateSourceOne() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, MybatisPlusProperties mybatisPlusProperties) throws Exception
    {
        String typeAliasesPackage = mybatisPlusProperties.getTypeAliasesPackage();
        String[] mapperLocations = mybatisPlusProperties.getMapperLocations();
        String configLocation = mybatisPlusProperties.getConfigLocation();
//        typeAliasesPackage = setTypeAliasesPackage(typeAliasesPackage);
//        VFS.addImplClass(SpringBootVFS.class);

        final MybatisSqlSessionFactoryBean sessionFactory2 = new MybatisSqlSessionFactoryBean();
        sessionFactory2.setDataSource(dataSource);
        sessionFactory2.setTypeAliasesPackage(typeAliasesPackage);
        sessionFactory2.setMapperLocations(resolveMapperLocations(mapperLocations));
//        sessionFactory2.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
        return sessionFactory2.getObject();

    /*final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
    sessionFactory.setMapperLocations(resolveMapperLocations(StringUtils.split(mapperLocations, ",")));
    sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
    return sessionFactory.getObject();*/
    }

    public Resource[] resolveMapperLocations(String[] mapperLocations ) {
        return (Resource[]) Stream.of((String[]) Optional.ofNullable(mapperLocations).orElse(new String[0])).flatMap((location) -> {
            return Stream.of(this.getResources(location));
        }).toArray((x$0) -> {
            return new Resource[x$0];
        });
    }
    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    private Resource[] getResources(String location) {
        try {
            return resourceResolver.getResources(location);
        } catch (IOException var3) {
            return new Resource[0];
        }
    }
    /**
     * 性能分析拦截器，不建议生产使用
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor(){
        return new PerformanceInterceptor();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
