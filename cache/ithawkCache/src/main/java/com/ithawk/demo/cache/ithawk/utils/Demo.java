package com.ithawk.demo.cache.ithawk.utils;

import java.util.function.Function;

/**
 * @author ithawk
 * @projectName cache
 * @description: TODO
 * @date 2021/8/911:31
 */
public class Demo {

    public static void dd(Function<String,String> function){
        System.out.println(function.apply("111"));
    }

    public static void main(String[] args) {
        dd(dd1("test"));
    }

    public static Function<String,String>  dd1(String f){
       return new Function<String, String>() {
           @Override
           public String apply(String s) {
               return f+s+"fffff";
           }
       };
    }

}
