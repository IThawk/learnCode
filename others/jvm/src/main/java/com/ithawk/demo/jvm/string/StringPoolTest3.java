package com.ithawk.demo.jvm.string;

public class StringPoolTest3 {

    public static void main(String[] args) throws Exception {
        String s1 = new String("aa") + new String("bb");
        String s3 = s1.intern();
        String s2 = new String("aa") + new String("bb");
		/*<p>
		   这个代码生成了5个对象：new String("aa") ： 生成一个new的String 对象在堆中，然后 在字符串常量池中的stringtable 生成一个 “aa"的引用地址
		   同理new String("bb") 也是一样
		   String s2 = new String("aa") + new String("bb") 会生成一个：new String 在堆中出现
		   所有就会生成5个

		   当调用.intern（）方法的时候，又会添加到 一个引用到在字符串常量池中的stringtable 生成一个 “aabb"的引用地址

		   但是java是一个关键字   jvm启动的时候就进入了stringtable
		 */
        String s4 = s2.intern();
        System.out.println("s1 == s2:" + s1 == s2);//false
        System.out.println("s1 == s3:" + s1 == s3);//true
        System.out.println("s2 == s4:" + s2 == s4);//false
        System.out.println("s3 == s4:" + s3 == s4);//true
        String s5 = new String("ja") + new String("va");
        String s6 = s5.intern();
        System.out.println("s5 == s6:" + s5 == s6);//false

        String s7 = new String("ja1");
        String s8 = s7.intern();
        System.out.println("s7 == s8:" + s7 == s8);//false
        System.in.read();

    }
}
