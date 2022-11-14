package com.ithawk.demo.cache.ithawk.listener;

import com.ithawk.demo.cache.ithawk.utils.LocalCaffeineCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;


/**
 * @className CacheMessageListener
 * @description:  定义缓存更新监听
 * @author IThawk
 * @date 2021/8/1 17:36
 */
@Slf4j
@Component
@Configuration
public class CacheMessageListener implements MessageListener {

	Logger logger =  LoggerFactory.getLogger(getClass());

	private RedisTemplate<String, String> redisTemplate;

	public CacheMessageListener(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer(RedisTemplate<String, String>  redisTemplate) {
		RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
		redisMessageListenerContainer.setConnectionFactory(redisTemplate.getConnectionFactory());
		CacheMessageListener cacheMessageListener = new CacheMessageListener(redisTemplate);
		redisMessageListenerContainer.addMessageListener(cacheMessageListener, new ChannelTopic("TOPIC"));
		return redisMessageListenerContainer;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		System.out.println("...........消费消息 清除本地缓存..........");
		CacheMessage cacheMessage = (CacheMessage) redisTemplate.getValueSerializer().deserialize(message.getBody());
		logger.debug("recevice a redis topic message, clear local cache, the cacheName is {}, the key is {}",
				cacheMessage.getCacheName(), cacheMessage.getKey());
		for (String key : cacheMessage.getKey()){
			LocalCaffeineCache.removeDataByKey(key);
		}

	}

}
