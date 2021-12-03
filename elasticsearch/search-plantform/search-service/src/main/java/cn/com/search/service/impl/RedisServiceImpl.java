package cn.com.search.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import cn.com.search.service.RedisService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

	@Resource
    private StringRedisTemplate stringRedisTemplate;
	
	@Resource
    private RedisTemplate<String,Object> redisTemplate;
	
	@Override
	public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
	}
	
	@Override
	public void set(String key, Object value, long expires) {
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        vo.set(key, value, expires, TimeUnit.SECONDS);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key, Class<T> clazz) {
		ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        return (T) vo.get(key);
	}

	@Override
	public Boolean delete(String key) {
		return redisTemplate.delete(key);
	}

	@Override
	public Boolean lock(String key, String value) {
		//SETNX命令, 可以设置返回true, 不可以返回false
		if(stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
			return true;
		}
		String currentValue = stringRedisTemplate.opsForValue().get(key);
		if(!StringUtils.isEmpty(currentValue) && (Long.parseLong(currentValue) < System.currentTimeMillis())) {
			//GETSET命令, 获取上一个锁的时间
			String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);
			if(!StringUtils.isEmpty(oldValue) && oldValue.equals(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void unLock(String key, String value) {
		try {
			String currentValue = stringRedisTemplate.opsForValue().get(key);
			if(!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
				stringRedisTemplate.opsForValue().getOperations().delete(key);
			}
		}catch(Throwable e) {
			log.error("[redis分布式锁] 解锁异常, {},{}", e.getMessage(), e);
		}
	}
}
