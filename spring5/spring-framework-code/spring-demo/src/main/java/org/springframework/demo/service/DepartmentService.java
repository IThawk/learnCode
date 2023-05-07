package org.springframework.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.demo.bean.Department;
import org.springframework.demo.dao.DepartmentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service(value = "departmentService")
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    public List<Department> getDepts() {

        List<Department> list = departmentMapper.selectByMap(null);
        return list;
    }
}
