package com.abc.service;

import com.abc.bean.Depart;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping("/fallback/consumer/depart")
public class DepartFallback implements DepartService {

    @Override
    public boolean saveDepart(Depart depart) {
        System.out.println("执行saveDepart()的服务降级方法 - class");
        return false;
    }

    @Override
    public boolean removeDepartById(Integer id) {
        System.out.println("执行removeDepartById()的服务降级方法 - class");
        return false;
    }

    @Override
    public boolean modifyDepart(Depart depart) {
        System.out.println("执行modifyDepart()的服务降级方法 - class");
        return false;
    }

    @Override
    public Depart getDepartById(Integer id) {
        System.out.println("执行getDepartById()的服务降级方法 - class");
        Depart depart = new Depart();
        depart.setId(id);
        depart.setName("no this depart -- class");
        return depart;
    }

    @Override
    public List<Depart> listAllDeparts() {
        System.out.println("执行listAllDeparts()的服务降级方法 - class");
        return null;
    }
}
