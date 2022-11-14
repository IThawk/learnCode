package com.ithawk.demo.springboot.cache.plugin.properties;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @className RedisConfigProp
 * @description:  
 * @author IThawk
 * @date 2021/8/1 17:34
 */
@Data
public class RedisConfigProp {

	/**
	 spring:
	   cache:
	     multi:
	       cachePrefix: test
	       redis:
	         default-expiration: 20000  # 20秒过期

	/**
	 * 全局过期时间，单位毫秒，默认不过期
	 */
	private long defaultExpiration = 0;

	/**
	 * 每个cacheName的过期时间，单位毫秒，优先级比defaultExpiration高
	 */
	private Map<String, Long> expires = new HashMap<>();

	/**
	 * 缓存更新时通知其他节点的topic名称
	 */
	private String topic = "cache:redis:caffeine:topic";

}
