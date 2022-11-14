package com.ithawk.demo.spring5.webflux.method;

public class Person {
    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    // 静态方法
    public static void sleeping(int hours) {
        System.out.println("人们每天需要睡眠" + hours + "小时");
    }

    // 实例方法
    // 在实例方法的第一个参数位置存在一个隐藏参数this
    public String play(Person this, int minutes) {
    // public String play(int minutes) {
        return name + "已经玩儿了" + minutes + "分钟了。";
    }

    // 实例方法
    public void study(Person this, String course) {
    // public void study(String course) {
        System.out.println(name + "正在学习" + course);
    }

    @Override
    public String toString() {
        return "Person{name=" + name + "}";
    }
}
