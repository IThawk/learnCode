package com.gupaoedu.crud.rowmapper;

import com.gupaoedu.crud.bean.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: qingshan
 * @Date: 2019/3/31 15:50
 * @Description: 咕泡学院，只为更好的你
 * 实现RowMapper接口
 */
public class EmployeeRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Employee employee = new Employee();
        employee.setEmpId(resultSet.getInt("emp_id"));
        employee.setEmpName(resultSet.getString("emp_name"));
        employee.setGender(resultSet.getString("gender"));
        employee.setEmail(resultSet.getString("email"));

        return employee;
    }
}
