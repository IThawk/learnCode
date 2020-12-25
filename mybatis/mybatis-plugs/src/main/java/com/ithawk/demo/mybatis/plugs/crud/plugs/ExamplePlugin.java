package com.ithawk.demo.mybatis.plugs.crud.plugs;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

@Intercepts({@Signature(
        type= Executor.class,
        method = "update",
        args = {MappedStatement.class,Object.class})})
public class ExamplePlugin implements Interceptor {
    private Properties properties = new Properties();
    public Object intercept(Invocation invocation) throws Throwable {
        // implement pre processing if need
        MappedStatement mappedStatement = (MappedStatement)  invocation.getArgs()[0];
//        System.out.println("拦截的SQL是"+(mappedStatement.getSqlSource()).get);

        BoundSql boundSql = (BoundSql) mappedStatement.getBoundSql(invocation.getArgs()[1]);
        Object o = invocation.getArgs()[1];
        String sql = boundSql.getSql().replaceAll("\\s+", " ").toLowerCase();
        Object returnObject = invocation.proceed();
        // implement post processing if need
        return returnObject;
    }
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }


    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}