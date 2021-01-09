package com.ithawk.demo.elasticsearch.springboot.pojo;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "stu", shards = 3, replicas = 0)
public class Stu implements Serializable {

    @Id
    private Long stuId;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String name;

    @Field
    private Integer age;

    @Field
    private Float money;

    @Field
    private boolean isMarried;

    public Stu(Long stuId, String name, Integer age, Float money, boolean isMarried) {
        this.stuId = stuId;
        this.name = name;
        this.age = age;
        this.money = money;
        this.isMarried = isMarried;
    }

    @Override
    public String toString() {
        return "Stu{" +
                "stuId=" + stuId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", money=" + money +
                ", isMarried=" + isMarried +
                '}';
    }

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }
}
