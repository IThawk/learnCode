package com.abc.provider.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// JPA的底层实现是Hibernate
// Hibernate默认对对象的查询开启了延迟加载
//  Depart depart = service.getDepartByid(3);
// String name3 = depart.getName();
@Data
@Entity   // 使用Hibernate的自动建表功能
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Depart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

}


