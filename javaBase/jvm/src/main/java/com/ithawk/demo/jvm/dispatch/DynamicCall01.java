package com.ithawk.demo.jvm.dispatch;

//调用方法
public class DynamicCall01 {
	public static void main(String[] args) {
		Father father = new Son();
		// 多态
		father.f1(); //发生了方法的重载，同时也发生了方法的重写 //invokevirtual
		// 打印结果： Son-f1()
		Father father1 = new Son1();
		father1.f2();
		System.out.printf(".....");
	}
}
