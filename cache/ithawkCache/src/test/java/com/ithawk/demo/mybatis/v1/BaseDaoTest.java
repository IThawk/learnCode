package com.ithawk.demo.mybatis.v1;

import com.alibaba.fastjson.JSON;
import com.ithawk.demo.cache.ithawk.daosupport.BaseDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BaseDaoTest {
    @Autowired
    private BaseDao baseDao;
    @Test
    public void EmployeeDaoSupportTest() {
        System.out.println(JSON.toJSONString(baseDao.selectList("select * from tbl_dept")));
    }

}
