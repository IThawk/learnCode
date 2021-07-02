package com.abc.provider.service;

import com.abc.provider.bean.Depart;
import com.abc.provider.repository.DepartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RefreshScope  // 实现数据的动态更新
public class DepartServiceImpl implements DepartService {
    @Autowired
    private DepartRepository repository;

    @Value("${depart.name}")
    private String departName;

    // 插入
    @Override
    public boolean saveDepart(Depart depart) {
        Depart obj = repository.save(depart);
        if(obj != null) {
            return true;
        }
        return false;
    }

    // 根据id删除
    @Override
    public boolean removeDepartById(int id) {

        if(repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // 修改
    @Override
    public boolean modifyDepart(Depart depart) {
        Depart obj = repository.save(depart);
        if(obj != null) {
            return true;
        }
        return false;
    }

    // 根据id查询
    @Override
    public Depart getDepartById(int id) {
        if(repository.existsById(id)) {
            Depart one = repository.getOne(id);
            one.setName(one.getName() + departName);
            return one;
        }
        Depart depart = new Depart();
        depart.setName("no this depart " + departName);
        return depart;
    }

    // 查询所有
    @Override
    public List<Depart> listAllDeparts() {
        return repository.findAll();
    }
}
