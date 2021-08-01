package com.ithawk.demo.springboot.cache.plugin.support;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @className CacheMessage
 * @description:  
 * @author IThawk
 * @date 2021/8/1 17:34
 */
@Data
@AllArgsConstructor
public class CacheMessage implements Serializable {

	private String cacheName;

	private Object key;

}
