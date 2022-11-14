package com.ithawk.demo.pattern.observer.guava;

import com.google.common.eventbus.Subscribe;

/**
 *
 */
public class GuavaEvent {

    @Subscribe
    public void subscribe(String str){
        System.out.println("执行subscribe方法，传入的参数是：" + str);
    }

}
