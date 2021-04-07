package com.ithawk.demo.pattern.observer.gperadvice;

/**
 *
 */
public class ObserverTest {
    public static void main(String[] args) {
        //实例化一个被观察对象（监听事件）
        GPer gper = GPer.getInstance();
        Teacher tom = new Teacher("Tom");
        Teacher jerry = new Teacher("Jerry");


        //这为没有
        Question question = new Question();
        question.setUserName("小明");
        question.setContent("观察者设计模式适用于哪些场景？");
        //添加监听者
        gper.addObserver(tom);
        gper.addObserver(jerry);
        //发布事件
        gper.publishQuestion(question);


    }

}
