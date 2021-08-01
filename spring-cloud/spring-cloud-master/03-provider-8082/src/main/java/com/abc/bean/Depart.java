package com.abc.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity  // 使用自动建表
// HttpMessageConverter   Jackson -> 完成Java对象与JSON数据间的转换工作
// JPA的默认实现是Hibernate，而Hibernate默认对于对象的查询是基于延迟加载的
// Depart depart = service.findById(5);   这里的depart实际是一个javasist动态代理对象
// String name = depart.getName();
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Depart {
    @Id  // 表示当前属性为自动建的表的主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 主键自动递增
    private Integer id;
    private String name;
}
