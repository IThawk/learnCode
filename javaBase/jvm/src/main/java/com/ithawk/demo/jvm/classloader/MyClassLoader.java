package com.ithawk.demo.jvm.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader {
    private String classpath;

    public MyClassLoader(String classpath) {

        this.classpath = classpath;
    }

	/**
	 * @className MyClassLoader
	 * @description:   自定义加载类路径
	 * @author IThawk
	 * @date 2021/11/16 19:00
	 */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("this is myClassLoader");
        try {
            byte[] classDate = getDate(name);
            if (classDate == null) {
            } else {
                // defineClass方法将字节码转化为类
                return defineClass(name, classDate, 0, classDate.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    // 返回类的字节码
    private byte[] getDate(String className) throws IOException {
        String path = classpath + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
        try (InputStream in = new FileInputStream(path); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
