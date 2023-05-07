package org.springframework.demo.service;


import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.demo.bean.Employee;
import org.springframework.demo.dao.EmployeeMapper;
import org.springframework.stereotype.Service;
//import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
@Service(value = "employeeService")
public class EmployeeService {


	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	@Autowired
	EmployeeMapper employeeMapper;

	public List<Employee> getAll() {

		return employeeMapper.selectByMap(null);
	}

	public void saveEmpsInfo(Employee employee) {
		logger.info("employee:{}", JSON.toJSONString(employee));
		employeeMapper.insertSelective(employee);
	}

	//检查用户名
	public boolean cheUser(String empName) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("empName", empName);

		long count = employeeMapper.countByMap(map);
		return count == 0;
	}

	//按id查询
	public Employee getEmp(Integer id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		return employee;
	}

	//员工更新
	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}

	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
	}

	public void deleteBatch(List<Integer> ids) {
		employeeMapper.deleteByList(ids);

	}
}
