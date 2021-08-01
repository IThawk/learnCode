package com.ithawk.demo.springboot.ex.service.impl;


import com.ithawk.demo.springboot.ex.bean.Employee;
import com.ithawk.demo.springboot.ex.dao.EmployeeDao;
import com.ithawk.demo.springboot.ex.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDao dao;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @CacheEvict(value = "realTimeCache", allEntries = true)
<<<<<<< HEAD
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addEmployee(Employee employee) throws Exception {
        dao.insertEmployee(employee);
=======
    @Transactional()
    @Override
    public void addEmployee(Employee employee) throws Exception {

        dao.insertEmployee(employee);
        //睡眠时间较长导致连接关闭        # 设置druid超时连接关闭
        //         remove-abandoned: true
        //         remove-abandoned-timeout-millis: 30000
        Thread.sleep(90000);
>>>>>>> aaec40860904e3b1ec6164df914652bc11c958bb
//        if (true) {
//            throw new Exception("发生受查异常");
//        }
//        dao.insertEmployee(employee);
    }

    @Cacheable(value = "realTimeCache", key = "'employee_'+#id")
    @Override
    public Employee findEmployeeById(int id) {
        return dao.selectEmployeeById(id);
    }

    // 使用双重检测锁解决热点缓存问题
    @Override
    public Integer findEmployeeCount() {
        // 获取Redis操作对象
        BoundValueOperations<Object, Object> ops = redisTemplate.boundValueOps("count");
        // 从缓存中读取数据
        Object count = ops.get();
        if (count == null) {
            synchronized (this) {
                count = ops.get();
                if (count == null) {
                    // 从DB中查询
                    count = dao.selectEmployeeCount();
                    // 将查询的数据写入到Redis缓存，并设置到期时限
                    ops.set(count, 10, TimeUnit.SECONDS);
                }
            }
        }
        return (Integer) count;
    }
}
