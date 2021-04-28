package com.ithawk.demo.thread.all;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterTest{

    private static AtomicIntegerFieldUpdater<User> a = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");

    public static void main(String[] args) {

        User user = new User("conan", 10);

        System.out.println(a.getAndIncrement(user));

        System.out.println(a.get(user));

    }



    public static class User {

        private String name;

        public volatile int age;



        public User(String name, int age) {

            this.name = name;

            this.age = age;

        }


        public String getName() {

            return name;

        }



        public int getAge() {

            return age;

        }

    }

}
