package com.ithawk.demo.thread.all;

import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentSkipListSetTest {
    public static void main(String[] args) {
        ConcurrentSkipListSet<Contact1> set = new ConcurrentSkipListSet<>();
        Thread threads[]=new Thread[25];
        int counter=0;
        //创建和启动25个任务，对于每个任务指定一个大写字母作为ID
        for (char i='A'; i<'Z'; i++) {
            Task1 task=new Task1(set, String.valueOf(i));
            threads[counter]=new Thread(task);
            threads[counter].start();
            counter++;
        }
        //使用join()方法等待线程的结束
        for (int i=0; i<25; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Size of the set: %d\n",set.size());
        Contact1 contact;
        // 使用first方法获取set的第一个实体，并输出。
        contact=set.first();
        System.out.printf("First Entry: %s: %s\n",contact.
                getName(),contact.getPhone());
        //使用last方法获取set的最后一个实体，并输出。
        contact=set.last();
        System.out.printf("Last Entry: %s: %s\n",contact.
                getName(),contact.getPhone());


    }

}

class Contact1 implements Comparable<Contact1> {
    private String name;
    private String phone;

    public Contact1(String name, String phone) {
        this.name = name;
        this.phone = phone;

    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public int compareTo(Contact1 o) {
        return name.compareTo(o.name);
    }
}

class Task1 implements Runnable {

    private ConcurrentSkipListSet<Contact1> set;
    private String id;

    public Task1(ConcurrentSkipListSet<Contact1> set, String id) {
        this.id = id;
        this.set = set;
    }

    @Override
    public void run() {

        for (int i = 0; i < 100; i++) {
            Contact1 contact = new Contact1(id, String.valueOf(i + 100));
            set.add(contact);
        }
    }
}





