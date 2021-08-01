package com.abc.service;

import com.abc.bean.Depart;
import com.abc.repository.DepartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartServiceImpl implements DepartService {

    @Autowired
    private DepartRepository repository;

    // 插入
    @Override
    public boolean saveDepart(Depart depart) {
        // 对于save()的参数，根据其id的不同，有以下三种情况：
        // depart的id为null：save()执行的是插入操作
        // depart的id不为null，且DB中该id存在：save()执行的是修改操作
        // depart的id不为null，但DB中该id不存在：save()执行的是插入操作，
        //      但其播入后的记录id值并不是这里指定的id，而是其根据指定的id生成策略所生成的id
        Depart obj = repository.save(depart);
        return obj != null ? true : false;
    }

    @Override
    public boolean removeDepartById(Integer id) {
        if(repository.existsById(id)) {
            // 在DB中指定的id若不存在，该方法会抛出异常
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyDepart(Depart depart) {
        Depart obj = repository.save(depart);
        return obj != null ? true : false;
    }

    @Override
    public Depart getDepartById(int id) {
        if(repository.existsById(id)) {
            // 在DB中指定的id若不存在，该方法会抛出异常
            return repository.getOne(id);
        }
        Depart depart = new Depart();
        depart.setName("no this depart");
        return depart;
    }

    @Override
    public List<Depart> listAllDeparts() {
        return repository.findAll();
    }
}
