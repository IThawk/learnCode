package org.springframework.demo.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.demo.bean.Employee;
import org.springframework.demo.dao.EmployeeMapper;
import org.springframework.demo.service.UserService;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private EmployeeMapper employeeMapper;
	Log log = LogFactory.getLog(UserServiceImpl.class);

	@Override
	public String userTest() {
		log.info("【请求 URL】");
		Employee employee = employeeMapper.selectByPrimaryKey(1);
		System.out.println(JSON.toJSONString(employee));
		return "test";
	}
}
