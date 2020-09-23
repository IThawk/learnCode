package com.ithawk.mybatis.demo.spi;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/24-21:07
 */
public class MysqlDriver implements DatabaseDriver<Mysql>{

    @Override
    public String name() {
        return "Mysql";
    }

    @Override
    public String buildConnect(String s) {
        return "Mysql Driver:"+s;
    }

    @Override
    public String sayHello() {
        return "HELLO MYSQL";
    }

    @Override
    public Mysql test(Mysql o) {
        return o;
    }
}
