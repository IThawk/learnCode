package com.ithawk.demo.springboot.cache.plugin.metrics;


import com.ithawk.demo.springboot.cache.plugin.support.RedisCaffeineCache;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import lombok.NoArgsConstructor;
import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;

/**
 * @className RedisCaffeineCacheMeterBinderProvider
 * @description:  
 * @author IThawk
 * @date 2021/8/1 17:33
 */
@NoArgsConstructor
public class RedisCaffeineCacheMeterBinderProvider implements CacheMeterBinderProvider<RedisCaffeineCache> {

    @Override
    public MeterBinder getMeterBinder(RedisCaffeineCache cache, Iterable<Tag> tags) {
        return new CaffeineCacheMetrics(cache.getCaffeineCache(), cache.getName(), tags);
    }
}
