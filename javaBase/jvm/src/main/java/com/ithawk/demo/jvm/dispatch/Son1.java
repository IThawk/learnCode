package com.ithawk.demo.jvm.dispatch;

public class Son1 extends Father{

	static {

		System.out.println("son-static");
	}
	public Son1() {
		System.out.println("son");
	}

	public void f1(char c) {
		System.out.println("Son-s1() para-char " + c);
	}
}
