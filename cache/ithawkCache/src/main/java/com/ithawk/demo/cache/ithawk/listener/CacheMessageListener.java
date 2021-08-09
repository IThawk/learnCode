package com.ithawk.demo.cache.ithawk.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/**
 * @className CacheMessageListener
 * @description:  定义缓存更新监听
 * @author IThawk
 * @date 2021/8/1 17:36
 */
@Slf4j
@Component
public class CacheMessageListener implements MessageListener {

	Logger logger =  LoggerFactory.getLogger(getClass());
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;


	@Override
	public void onMessage(Message message, byte[] pattern) {
		CacheMessage cacheMessage = (CacheMessage) redisTemplate.getValueSerializer().deserialize(message.getBody());
		logger.debug("recevice a redis topic message, clear local cache, the cacheName is {}, the key is {}",
				cacheMessage.getCacheName(), cacheMessage.getKey());
//		redisCaffeineCacheManager.clearLocal(cacheMessage.getCacheName(), cacheMessage.getKey());
	}

}
