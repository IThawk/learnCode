package com.abc.fallback;

import com.abc.bean.Depart;

import java.util.ArrayList;
import java.util.List;

public class DepartServiceFallback {
    // 降级方法是静态方法
    public static Depart getFallback(int id, Throwable th) {
        System.out.println("getHandle()执行异常 " + id);
        Depart depart = new Depart();
        depart.setId(id);
        depart.setName("degrade-class-" + id + " th " + th);
        return depart;
    }

    public static List<Depart> listFallback() {
        System.out.println("listHandle()执行异常");
        List<Depart> list = new ArrayList();
        Depart depart = new Depart();
        depart.setName("no any depart");
        list.add(depart);
        return list;
    }
}
