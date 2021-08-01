package com.kkb.mybatis.io;

import java.io.InputStream;

public class Resources {

	public static InputStream getResource(String location) {
		InputStream inputStream = Resources.class.getClassLoader().getResourceAsStream(location);
		return inputStream;
	}
}
