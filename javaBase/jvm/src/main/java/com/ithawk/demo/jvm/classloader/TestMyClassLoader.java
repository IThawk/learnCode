package com.ithawk.demo.jvm.classloader;

import java.lang.reflect.Method;

public class TestMyClassLoader {

	public static void main(String[] args) throws Exception {
		// 自定义类加载器的加载路径 D:\workspace\language\github\learnCode\others\jvm\target\classes\TestJVM.class
		MyClassLoader myClassLoader = new MyClassLoader("D:\\workspace\\language\\github\\learnCode\\others\\jvm\\target\\classes");
		// 包名+类名
		Class<?> c = myClassLoader.loadClass("TestJVM");

		if (c != null) {
			Object obj = c.newInstance();
			Method method = c.getMethod("say", null);
			method.invoke(obj, null);
			System.out.println(c.getClassLoader().toString());
		}
		Class<?> c2 = Class.forName("TestJVM");
		if (c2 != null) {
			Object obj = c2.newInstance();
			Method method = c2.getMethod("say", null);
			method.invoke(obj, null);
			System.out.println(c2.getClassLoader().toString());
		}
	}
}
