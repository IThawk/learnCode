package com.ithawk.demo.spring.v1.dbutils;

import com.ithawk.demo.spring.v1.dbutils.dao.BlogDao;

public class Main {
    public static void main(String[] args) throws Exception {
        HikariUtil.init();
        BlogDao.selectBlog(1);
        BlogDao.selectList();
    }
}
