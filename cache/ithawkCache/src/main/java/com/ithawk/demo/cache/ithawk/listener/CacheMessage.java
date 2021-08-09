package com.ithawk.demo.cache.ithawk.listener;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @className CacheMessage
 * @description:  
 * @author IThawk
 * @date 2021/8/1 17:34
 */
//@Data
//@AllArgsConstructor
public class CacheMessage implements Serializable {

	private String cacheName;

	private Object key;

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}
}
