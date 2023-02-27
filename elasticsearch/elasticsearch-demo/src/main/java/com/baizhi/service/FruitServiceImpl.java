package com.baizhi.service;

import com.baizhi.dao.FruitDao;
import com.baizhi.entity.Fruit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FruitServiceImpl  implements FruitService{

    private final FruitDao fruitDao;

    @Autowired
    public FruitServiceImpl(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Fruit> findAll() {
        return this.fruitDao.findAll();
    }
}
