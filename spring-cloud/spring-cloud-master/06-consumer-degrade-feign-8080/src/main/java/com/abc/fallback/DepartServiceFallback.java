package com.abc.fallback;

import com.abc.bean.Depart;
import com.abc.service.DepartService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping("/fallback/consumer/depart")  // 必须以 /fallback 开头
public class DepartServiceFallback implements DepartService {   // 实现Feign接口
    @Override
    public boolean saveDepart(Depart depart) {
        System.out.println("执行saveDepart()的服务降级处理方法");
        return false;
    }

    @Override
    public boolean removeDepartById(int id) {
        System.out.println("执行removeDepartById()的服务降级处理方法");
        return false;
    }
    @Override
    public boolean modifyDepart(Depart depart) {
        System.out.println("执行modifyDepart()的服务降级处理方法");
        return false;
    }

    @Override
    public Depart getDepartById(int id) {
        System.out.println("执行getDepartById()的服务降级处理方法");
        Depart depart = new Depart();
        depart.setId(id);
        depart.setName("degrade-feign");
        return depart;
    }

    @Override
    public List<Depart> listAllDeparts() {
        System.out.println("执行listAllDeparts()的服务降级处理方法");
        return null;
    }
}
