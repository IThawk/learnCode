package com.ithawk.demo.spring5.webflux.method;

import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class MethodTest {

    @Test
    public void test01() {
        Person person = new Person("张三");
        System.out.println(person.play(5));
        person.study("webflux");
    }

    // Lambda静态方法引用   类名::静态方法名
    @Test
    public void test02() {
        // sleeping()方法只有一个输入，没有输出，符合函数式接口Consumer的定义
       Consumer<Integer> con = Person::sleeping;
       con.accept(8);   // 相当于  Person.sleeping(8)
    }

    // Lambda实例方法引用   实例名::实例方法名
    @Test
    public void test03() {
        Person person = new Person("李四");
        // play()方法只有一个输入，且有输出，符合函数式接口Function的定义
        Function<Integer, String> fun = person::play;
        System.out.println(fun.apply(5));    // 相当于person.play(5)
    }

    // Lambda实例方法引用   类名::实例方法名
    @Test
    public void test04() {
        Person person = new Person("李四");
        // play()方法只有一个输入，且有输出，符合函数式接口Function的定义
        BiFunction<Person, Integer, String> bf = Person::play;
        System.out.println(bf.apply(person, 5));    // 相当于person.play(5)
    }

    // Lambda实例方法引用   实例名::实例方法名
    @Test
    public void test05() {
        Person person = new Person("李四");
        // study()方法只有一个输入，没有输出，符合函数式接口Consumer的定义
       Consumer<String> con = person::study;
       con.accept("WebFlux");    // 相当于person.study("WebFlux")
    }

    // Lambda无参构造器引用   类名::new
    @Test
    public void test06() {
       // 无参构造器是没有输入，但有输出，其符合函数式接口Supplier的定义
        Supplier<Person> supp = Person::new;
        System.out.println(supp.get());    // 相当于  new Person()
    }

    // Lambda带参构造器引用   类名::new
    @Test
    public void test07() {
        // 带参构造器是仅有一个输入，且有输出，其符合函数式接口Function的定义
        Function<String, Person> fun = Person::new;
        System.out.println(fun.apply("王五"));    // 相当于new Person("王五")
    }
}
