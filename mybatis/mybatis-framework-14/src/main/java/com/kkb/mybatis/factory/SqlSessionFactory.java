package com.kkb.mybatis.factory;

import com.kkb.mybatis.sqlsession.SqlSession;

/**
 * 主要用来生产SqlSession
 * 
 * @author 灭霸詹
 *
 */
public interface SqlSessionFactory {

	SqlSession openSession();
}
