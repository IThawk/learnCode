package com.ithawk.demo.springboot.cache.plugin.metrics;

import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @className RedisCaffeineCacheMeterConfiguration
 * @description:  
 * @author IThawk
 * @date 2021/8/1 17:33
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({MeterBinder.class, CacheMeterBinderProvider.class})
//@EnableConfigurationProperties({CacheConfigProperties.class, CaffeineConfigProp.class, RedisConfigProp.class})
public class RedisCaffeineCacheMeterConfiguration {

	@Bean
	public RedisCaffeineCacheMeterBinderProvider redisCaffeineCacheMeterBinderProvider() {
		return new RedisCaffeineCacheMeterBinderProvider();
	}

}
