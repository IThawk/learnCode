package com.gupaoedu;

import com.gupaoedu.crud.bean.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @Author: qingshan
 * @Date: 2018/11/9 17:31
 * @Description: 咕泡学院，只为更好的你
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class JdbcTemplteBatchInsertTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    List<Employee> list;

    private static final int USER_COUNT = 100000; // 数量过大可能导致内存溢出，多运行几次

    @Test
    public void testBatchInsert() {
        long start = System.currentTimeMillis();
        list = new ArrayList<>();
        for (int i=0; i< USER_COUNT; i++){
            Employee employee = new Employee();
            String name = UUID.randomUUID().toString();
            employee.setEmpName(name);
            String gender = i%2 == 0 ? "M" : "F";
            employee.setGender(gender);
            employee.setEmail("better@gupaoedu.com");
            Integer did = i%2 == 0 ? 1 : 2;
            employee.setdId(did);
            list.add(employee);
        }

        save(list);
        long end = System.currentTimeMillis();
        System.out.println("批量插入"+USER_COUNT+"条员工数据完毕，总耗时：" + (end - start) + " 毫秒");

    }

    /**
     * 必须要在数据库连接url加上 &rewriteBatchedStatements=true 来开启批处理，否则还是一条一条写入的
     * @param list
     */
    public void save(List<Employee> list) {
        final List<Employee> tempList = list;
        String sql = "insert into tbl_emp(emp_name, gender, email,d_id) "
                + "values(?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                String empName =  tempList.get(i).getEmpName();
                String gender = tempList.get(i).getGender();
                String email = tempList.get(i).getEmail();
                Integer did = tempList.get(i).getdId();

                ps.setString(1, empName);
                ps.setString(2, gender);
                ps.setString(3,email);
                ps.setInt(4, did);
            }

            public int getBatchSize() {
                return tempList.size();
            }
        });

    }
}
