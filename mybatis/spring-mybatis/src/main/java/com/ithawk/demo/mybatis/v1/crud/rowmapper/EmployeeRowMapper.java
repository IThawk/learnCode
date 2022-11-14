package com.ithawk.demo.mybatis.v1.crud.rowmapper;

import com.ithawk.demo.mybatis.v1.crud.bean.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
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
