package com.ithawk.springboot.demo.orm.mybatis;

import cn.hutool.json.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class TestClone<T> {
    public static void main(String[] args) {
        List<D> list = new ArrayList<>();
        list.add(new D().setString("test"));
        List<D> newList = new ArrayList<>();

//        CollectionUtils.addAll(newList, new Object[list.size()]);
        BeanUtils.copyProperties(list, newList);
        newList.forEach(i->{
            i.setString("test1");

        });
        System.out.println();
    }

    static class D implements Cloneable {
        private String string;

        public String getString() {
            return string;
        }

        public D setString(String string) {
            this.string = string;
            return this;
        }

        @Override
        public D clone() {
            D myClass = null;
            try {
                myClass = (D) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            /*如果类中存在非基本类型的属性，需要在这里对属性进行克隆（通过调用属性的clone()方法。*/
            return myClass;

        }
    }


    /**
     * 使用序列化技术实现深拷贝
     * @return
     */
    public T deepClone() throws IOException,ClassNotFoundException{
        //将对象写入流中
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(this);
        //从流中取出
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        return (T)objectInputStream.readObject();

    }
}
