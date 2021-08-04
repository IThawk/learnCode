package com.ithawk.demo.jvm.string;

public class StringPoolTest3 {

	public static void main(String[] args) throws Exception {
		String s1 = new String("aa") + new String("bb");
		String s3 = s1.intern();
		String s2 = new String("aa") + new String("bb");
		String s4 = s2.intern();
		System.out.println(s1 == s2);//false
		System.out.println(s1 == s3);//true
		System.out.println(s2 == s4);//false
		System.out.println(s3 == s4);//true
		System.in.read();
		
	}
}
