package com.ithawk.demo.cache.ithawk.daosupport;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Repository
public  class BaseDao extends SqlSessionDaoSupport {
    //使用sqlSessionFactory
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    /**
     * 获取Object对象
     *
     * @param statement
     * @return
     */
    public Object selectOne(String statement) {
        return getSqlSession().selectOne(statement);
    }

    public Object selectOne(String statement, Object parameter) {
        return getSqlSession().selectOne(statement, parameter);
    }

    /**
     * 执行insert操作
     *
     * @param statement
     * @return
     */
    public int insert(String statement) {
        return getSqlSession().insert(statement);
    }

    /**
     * 执行insert操作
     *
     * @param statement
     * @param parameter
     * @return
     */
    public int insert(String statement, Object parameter) {
        return getSqlSession().insert(statement, parameter);
    }

    public int update(String statement) {
        return getSqlSession().update(statement);
    }

    public int update(String statement, Object parameter) {
        return getSqlSession().update(statement, parameter);
    }

    public int delete(String statement) {
        return getSqlSession().delete(statement);
    }

    public int delete(String statement, Object parameter) {
        return getSqlSession().delete(statement, parameter);
    }

    /**
     * 获取一个list集合
     *
     * @param statement
     * @return
     */
    public List<?> selectList(String statement) {
        return getSqlSession().selectList(statement);
    }

    /**
     * 根据参数 获取一个list集合
     *
     * @param statement
     * @param parameter
     * @return
     */
    public List<?> selectList(String statement, Object parameter) {
        return getSqlSession().selectList(statement, parameter);
    }

    public Map<?, ?> selectMap(String statement, String mapKey) {
        return getSqlSession().selectMap(statement, mapKey);
    }

    public Map<?, ?> selectMap(String statement, Object parameter, String mapKey) {
        return getSqlSession().selectMap(statement, parameter, mapKey);
    }

    /**
     * 获取connection, 以便执行较为复杂的用法
     *
     * @return
     */
    public Connection getConnection() {
        return getSqlSession().getConnection();
    }

    public static void main(String[] args) {
        Integer a = Integer.MAX_VALUE;
        int MAXIMUM_CAPACITY = -8 >>> 1;
        System.out.println(MAXIMUM_CAPACITY);
//        a= a+1;
        System.out.println(a);
        a= a*31;
        System.out.println(a);
    }
}