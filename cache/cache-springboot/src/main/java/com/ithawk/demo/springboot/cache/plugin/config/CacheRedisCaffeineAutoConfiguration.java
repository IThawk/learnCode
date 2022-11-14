package com.ithawk.demo.springboot.cache.plugin.config;

import com.ithawk.demo.springboot.cache.plugin.properties.CacheConfigProperties;
import com.ithawk.demo.springboot.cache.plugin.support.CacheMessageListener;
import com.ithawk.demo.springboot.cache.plugin.support.RedisCaffeineCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;



@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(CacheConfigProperties.class)//定义加载配置类
public class CacheRedisCaffeineAutoConfiguration {

	@Autowired
	private CacheConfigProperties cacheRedisCaffeineProperties;

	/**
	 * @description:  定义一个
	  * @param redisTemplate
	 * @return: com.ithawk.demo.springboot.cache.plugin.support.RedisCaffeineCacheManager
	 * @author IThawk
	 * @date: 2021/8/1 20:52
	 */
	@Bean
	@ConditionalOnBean(RedisTemplate.class)//定义RedisTemplate 已经 加载完成之后 才会初始化化这个bean
	public RedisCaffeineCacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
		//新建缓存管理器
		return new RedisCaffeineCacheManager(cacheRedisCaffeineProperties, redisTemplate);
	}

	/**
	 * @description:  注册redis pub/sub 监听
	  * @param redisTemplate
	 * @param redisCaffeineCacheManager
	 * @return: org.springframework.data.redis.listener.RedisMessageListenerContainer
	 * @author IThawk
	 * @date: 2021/8/1 21:03
	 */
	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer(RedisTemplate<Object, Object> redisTemplate,
			RedisCaffeineCacheManager redisCaffeineCacheManager) {
		RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
		redisMessageListenerContainer.setConnectionFactory(redisTemplate.getConnectionFactory());
		CacheMessageListener cacheMessageListener = new CacheMessageListener(redisTemplate, redisCaffeineCacheManager);
		redisMessageListenerContainer.addMessageListener(cacheMessageListener, new ChannelTopic(cacheRedisCaffeineProperties.getRedis().getTopic()));
		return redisMessageListenerContainer;
	}
}
