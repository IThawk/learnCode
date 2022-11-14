package com.ithawk.demo.springcloud.provider.service;



import com.ithawk.demo.springcloud.provider.bean.Depart;

import java.util.List;

public interface DepartService {
    boolean saveDepart(Depart depart);
    boolean removeDepartById(Integer id);
    boolean modifyDepart(Depart depart);
    Depart getDepartById(int id);
    List<Depart> listAllDeparts();
}
