package com.ithawk.demo.mybatis.v1.crud.bean;


import java.io.Serializable;
import java.util.List;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-12-02 19:51
 */
public class HawkObject implements Serializable {
    String id;
    String name;
    String relateId;
    String des;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    List<HawkObject> hawkTreeList;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelateId() {
        return relateId;
    }

    public void setRelateId(String relateId) {
        this.relateId = relateId;
    }

    public List<HawkObject> getHawkTreeList() {
        return hawkTreeList;
    }

    public void setHawkTreeList(List<HawkObject> hawkTreeList) {
        this.hawkTreeList = hawkTreeList;
    }



}
