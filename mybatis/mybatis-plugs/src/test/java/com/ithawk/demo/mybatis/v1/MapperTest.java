package com.ithawk.demo.mybatis.v1;

import com.alibaba.fastjson.JSON;
import com.ithawk.demo.mybatis.plugs.crud.bean.Department;
import com.ithawk.demo.mybatis.plugs.crud.bean.Employee;
import com.ithawk.demo.mybatis.plugs.crud.bean.HawkObject;
import com.ithawk.demo.mybatis.plugs.crud.dao.DepartmentMapper;
import com.ithawk.demo.mybatis.plugs.crud.dao.EmployeeMapper;
import com.ithawk.demo.mybatis.plugs.crud.dao.HawkObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 测试dao层
 *
 * <p>
 * 1、导入Spring  test模块
 * 2、ContextConfiguration 指定Spring配置文件的位置
 * 3、Autowired
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {
    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    HawkObjectMapper hawkObjectMapper;

    /**
     * 循环插入
     */
    @Test
    public void testCRUD1() {
       Integer i =  new Random().nextInt(100)-2;
        int employee =  departmentMapper.insert(new Department(i,"ddd"));
    }

    /**
     * 循环插入
     */
    @Test
    public void testCRUD() {
        long start = System.currentTimeMillis();
        int count = 10;
        for (int i=0; i< count; i++) {
            String gender = i%2 == 0 ? "M" : "F";
            employeeMapper.insertSelective(new Employee(null, "TestName"+i, gender, "mahuateng@baidu.com", 1));
        }
        long end = System.currentTimeMillis();
        System.out.println("循环批量插入"+count+"条，耗时：" + (end -start )+"毫秒");
    }

    /**
     * 单条SQL批量插入
     */
    @Test
    public void testBatchInsert() {
        List<Employee> list = new ArrayList<Employee>();
        long start = System.currentTimeMillis();
        int count = 1000;
        // max_allowed_packet 默认 4M，所以超过长度会报错
        for (int i=0; i< count; i++) {
            String gender = i%2 == 0 ? "M" : "F";
            Integer did = i%2 == 0 ?  1 : 2;
            Employee emp = new Employee(null, "TestName"+i, gender, "pony@baidu.com", did);
            list.add(emp);
        }

        employeeMapper.batchInsert(list);
        long end = System.currentTimeMillis();
        System.out.println("批量插入"+count+"条，耗时：" + (end -start )+"毫秒");

    }

    /**
     * 批量更新
     */
    @Test
    public void testUpdateInsert() {
        List<Employee> list = new ArrayList<Employee>();
        long start = System.currentTimeMillis();
        int count = 3;
        // max_allowed_packet 默认 4M，所以超过长度会报错
        for (int i=1; i< count+1; i++) {
            String gender = i%2 == 0 ? "F" : "M";
            Integer did = i%2 == 0 ?  2 : 1;
            Employee emp = new Employee(i, "nameu", gender, "a@b.com", did);
            list.add(emp);
        }

        employeeMapper.updateBatch(list);
        long end = System.currentTimeMillis();
        System.out.println("批量更新"+count+"条，耗时：" + (end -start )+"毫秒");
    }

    /**
     * 批量更新
     */
    @Test
    public void selectById() {


        List<HawkObject> start =  hawkObjectMapper.selectById("4");
        System.out.println("JSON");
    }



}
