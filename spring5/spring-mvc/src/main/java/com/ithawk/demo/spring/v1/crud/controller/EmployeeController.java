package com.ithawk.demo.spring.v1.crud.controller;

import com.ithawk.demo.spring.v1.crud.bean.Employee;
import com.ithawk.demo.spring.v1.crud.bean.Msg;
import com.ithawk.demo.spring.v1.crud.config.MyThreadPoolExecutor;
import com.ithawk.demo.spring.v1.crud.config.SpringPool;
import com.ithawk.demo.spring.v1.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 员工信息管理
 */
@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    MyThreadPoolExecutor myThreadPoolExecutor;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /*
     * 单个 或批量删除
     * 批量：1-2-3，以-隔开
     * 单个删除：1
     * */
    @ResponseBody
    @RequestMapping(value = "/emp/{ids}", method = RequestMethod.DELETE)
    public Msg deleteEmp(@PathVariable("ids") String ids) {
        if (ids.contains("-")) {
            //批量删除
            List<Integer> del_ids = new ArrayList<>();
            String[] str_ids = ids.split("-");
            //组装id的集合
            for (String string : str_ids) {
                del_ids.add(Integer.parseInt(string));
            }
            employeeService.deleteBatch(del_ids);
        } else {
            //单个删除
            Integer id = Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }
        return Msg.success();
    }

    @ResponseBody
    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
    public Msg saveEmp(Employee employee) {
        employeeService.updateEmp(employee);
        return Msg.success();
    }

    //根据id查询员工
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Msg geEmp(@PathVariable("id") Integer id) {
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp", employee);
    }

    /**
     * @param pn
     * @return
     */
    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn)  {
        PageHelper.startPage(pn, 10); //pageNumber, pageSize，第几页，每页几条
        List<Employee> emps = employeeService.getAll();
        PageInfo page = new PageInfo(emps, 10);
        myThreadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("删除数据");
                employeeService.deleteEmp(2);
                System.out.println("eeeee 线程执行");
            }
        });

        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("删除数据");
            }
        });
        return Msg.success().add("pageInfo", page);
    }

    /**
     * 查询员工数据 分页
     *
     * @param pn
     * @param model
     * @return
     * @RequestMapping("/emps")
     */
    public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
        PageHelper.startPage(pn, 10);
        List<Employee> emps = employeeService.getAll();
        PageInfo page = new PageInfo(emps, 10);
        //连续显示的页数是10页
        //包装查出来的结果，只需要将pageInfo交给页面，封装了详细的分页信息
        //包括查询出来的数据
        model.addAttribute("pageInfo", page);

        return "list";
    }

    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public Msg insertEmpsInfo(Employee employee, BindingResult result) {
        employeeService.saveEmpsInfo(employee);
        return Msg.success();
    }

    @ResponseBody
    @RequestMapping("/checkUser")
    public Msg checkEmp(@RequestParam("empName") String empName) {
        //数据库用户名校验
        boolean b = employeeService.cheUser(empName);
        if (b) {
            return Msg.success();
        } else {
            return Msg.fail();
        }
    }

}
