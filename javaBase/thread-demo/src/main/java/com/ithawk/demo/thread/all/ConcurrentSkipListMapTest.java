package com.ithawk.demo.thread.all;

import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, Contact> map = new ConcurrentSkipListMap<>();
        Thread threads[]=new Thread[25];
        int counter=0;
        //创建和启动25个任务，对于每个任务指定一个大写字母作为ID
        for (char i='A'; i<'Z'; i++) {
            Task0 task=new Task0(map, String.valueOf(i));
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
        System.out.printf("Size of the map: %d\n",map.size());
        Map.Entry<String, Contact> element;
        Contact contact;
        // 使用firstEntry()方法获取map的第一个实体，并输出。
        element=map.firstEntry();
        contact=element.getValue();
        System.out.printf("First Entry: %s: %s\n",contact.
                getName(),contact.getPhone());
        //使用lastEntry()方法获取map的最后一个实体，并输出。
        element=map.lastEntry();
        contact=element.getValue();
        System.out.printf("Last Entry: %s: %s\n",contact.
                getName(),contact.getPhone());
        //使用subMap()方法获取map的子map，并输出。

        System.out.printf("Submap from A1996 to B1002: \n");

        ConcurrentNavigableMap<String, Contact> submap=map.

                subMap("A1996", "B1001");

        do {
            element=submap.pollFirstEntry();
            if (element!=null) {
                contact=element.getValue();
                System.out.printf("%s: %s\n",contact.getName(),contact.
                        getPhone());
            }
        } while (element!=null);
    }

}

class Contact {
    private String name;
    private String phone;

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;

    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}

class Task0 implements Runnable {

    private ConcurrentSkipListMap<String, Contact> map;
    private String id;

    public Task0(ConcurrentSkipListMap<String, Contact> map, String id) {
        this.id = id;
        this.map = map;
    }

    @Override
    public void run() {

        for (int i = 0; i < 1000; i++) {
            Contact contact = new Contact(id, String.valueOf(i + 1000));
            map.put(id + contact.getPhone(), contact);
        }
    }
}




