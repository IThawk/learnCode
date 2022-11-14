package com.ithawk.demo.mybatis.v1;

import com.ithawk.demo.mybatis.v1.crud.daosupport.EmployeeDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DaoSupportTest {
    @Autowired
    EmployeeDaoImpl employeeDao;

    @Test
    public void EmployeeDaoSupportTest() {
        System.out.println(employeeDao.selectByPrimaryKey(1));
    }




}
