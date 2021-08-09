package com.ithawk.demo.cache.ithawk.service;

import com.alibaba.fastjson.JSON;
import com.ithawk.demo.cache.ithawk.bean.Department;
import com.ithawk.demo.cache.ithawk.dao.DepartmentMapper;
import com.ithawk.demo.cache.ithawk.rowmapper.BaseRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 *
 */
@Service
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    public List<Department> getDepts() {

        List<Department> list = departmentMapper.selectByMap(null);
        return list;
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TransactionTemplate transactionTemplate;

    public List<Department> getDeptsBysql() {
        List<Department> a = (List<Department>) jdbcTemplate.query("select * from tbl_dept", new BaseRowMapper(Department.class));
        System.out.println(JSON.toJSONString(a));
        return a;
    }


    public void insertDeptsBysql() {
        try {
            transactionTemplate.execute(new TransactionCallback<Object>() {
                @Override
                public Object doInTransaction(TransactionStatus status) {
                    try {
                        jdbcTemplate.execute("INSERT INTO `tbl_dept`(`dept_id`, `dept_name`) VALUES (3, '开发部2')");
                    }catch (Throwable e){
                        System.out.println(e.toString());
                        status.setRollbackOnly();
                        return "do Transaction rollBack";
                    }

                    return "do Transaction complete";
                }
            });
        }catch (Exception e){
            System.out.println(e.toString());
        }

        System.out.println("..................");

    }
}
