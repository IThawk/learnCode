package com.ithawk.demo.pattern.proxy.staticproxy;

import com.ithawk.demo.pattern.proxy.Person;

/**
 * 静态代理
 */
public class Father implements Person {
    private Son person;

    public Father(Son person){
        this.person = person;
    }

    public void findLove(){
        System.out.println("父亲物色对象");
        this.person.findLove();
        System.out.println("双方父母同意，确立关系");
    }

    public void findJob(){

    }


}
