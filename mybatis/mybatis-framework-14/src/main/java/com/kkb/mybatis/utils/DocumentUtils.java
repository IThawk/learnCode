package com.kkb.mybatis.utils;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

public class DocumentUtils {

	public static Document createDocument(InputStream inputStream) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(inputStream);
			return document;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
