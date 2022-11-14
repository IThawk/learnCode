package com.ithawk.demo.elasticsearch.springboot.pojo;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

@Document(indexName = "car", type = "_doc", createIndex = false)
public class Car {

    @Id
    private Long userId;

    @Field(store = true)
    private String userName;

    @Field(store = true)
    @GeoPointField
    private GeoPoint geo;

    @Field(store = true)
    private String place;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public GeoPoint getGeo() {
        return geo;
    }

    public void setGeo(GeoPoint geo) {
        this.geo = geo;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Car{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", geo=" + geo +
                ", place='" + place + '\'' +
                '}';
    }
}
