package com.ithawk.demo.serializer.bean;

import java.io.IOException;
import java.io.Serializable;


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
