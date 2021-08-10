package com.ithawk.demo.cache.ithawk.listener;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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

	private List<String> key;

	public CacheMessage(String cacheName, List<String> key) {
		this.cacheName = cacheName;
		this.key = key;
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public List<String> getKey() {
		return key;
	}

	public void setKey(List<String> key) {
		this.key = key;
	}
}
