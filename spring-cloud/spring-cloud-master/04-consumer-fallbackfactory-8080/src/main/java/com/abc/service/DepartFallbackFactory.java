package com.abc.service;

import com.abc.bean.Depart;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 服务降级类
 */
@Component
public class DepartFallbackFactory implements FallbackFactory<DepartService> {
    @Override
    public DepartService create(Throwable throwable) {
        return new DepartService() {
            @Override
            public boolean saveDepart(Depart depart) {
                System.out.println("执行saveDepart()的服务降级处理方法");
                return false;
            }

            @Override
            public boolean removeDepartById(Integer id) {
                System.out.println("执行removeDepartById()的服务降级处理方法");
                return false;
            }

            @Override
            public boolean modifyDepart(Depart depart) {
                System.out.println("执行modifyDepart()的服务降级处理方法");
                return false;
            }

            @Override
            public Depart getDepartById(Integer id) {
                Depart depart = new Depart();
                depart.setId(id);
                depart.setName("no this depart -- class");
                return depart;
            }

            @Override
            public List<Depart> listAllDeparts() {
                System.out.println("执行listAllDeparts()的服务降级处理方法");
                return null;
            }
        };
    }
}
