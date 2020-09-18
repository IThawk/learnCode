package com.gupaoedu;

import com.gupaoedu.crud.daosupport.EmployeeDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: qingshan
 * @Date: 2019/3/9 15:04
 * @Description: 咕泡学院，只为更好的你
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
