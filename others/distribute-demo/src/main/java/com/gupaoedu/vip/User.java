package com.gupaoedu.vip;

import java.io.IOException;
import java.io.Serializable;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class User implements Serializable {


    private static final long serialVersionUID = -434539422310062943L;

    private String name;
    private int age;


    private void writeObject(java.io.ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();

        s.writeObject(name);
    }

    private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        name=(String)s.readObject();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
