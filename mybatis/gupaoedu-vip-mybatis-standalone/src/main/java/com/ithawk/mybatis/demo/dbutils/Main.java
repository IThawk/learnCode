package com.ithawk.mybatis.demo.dbutils;

import com.ithawk.mybatis.demo.dbutils.dao.BlogDao;

public class Main {
    public static void main(String[] args) throws Exception {
        HikariUtil.init();
        BlogDao.selectBlog(1);
        BlogDao.selectList();
    }
}
