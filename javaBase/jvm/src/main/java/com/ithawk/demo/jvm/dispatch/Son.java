package com.ithawk.demo.jvm.dispatch;

public class Son extends Father{
	Father father = new Father();

	Father1 father1 = new Father1();
	static {

		System.out.println("son-static");
	}
	public Son() {
		System.out.println("son");
	}

	public void f1(char c) {
		System.out.println("Son-s1() para-char " + c);
	}
}
