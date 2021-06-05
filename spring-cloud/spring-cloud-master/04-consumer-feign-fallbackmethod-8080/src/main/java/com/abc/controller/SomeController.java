package com.abc.controller;

import com.abc.bean.Depart;
import com.abc.service.DepartService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumer/depart")
public class SomeController {

    @Autowired
    private DepartService service;

    @PostMapping("/save")
    public boolean saveHandler(@RequestBody Depart depart) {
        return service.saveDepart(depart);
    }

    @DeleteMapping("/del/{id}")
    public boolean deleteHandler(@PathVariable("id") int id) {
        return service.removeDepartById(id);
    }

    @PutMapping("/update")
    public boolean updateHandler(@RequestBody Depart depart) {
        return service.modifyDepart(depart);
    }

    // 指定该方法要使用服务降级。即当前处理器方法在运行过程中若发生异常，
    // 无法给客户端正常响应时，就会调用fallbackMethod指定的方法
    @HystrixCommand(fallbackMethod = "getHystrixHandler")
    @GetMapping("/get/{id}")
    public Depart getByIdHandler(@PathVariable("id") int id) {
        return service.getDepartById(id);
    }

    // 定义服务降级方法，即响应给客户端的备选方案
    public Depart getHystrixHandler(@PathVariable("id") int id) {
        Depart depart = new Depart();
        depart.setId(id);
        depart.setName("no this depart");
        return depart;
    }

    @GetMapping("/list")
    public List<Depart> listHandler() {
        return service.listAllDeparts();
    }
}



