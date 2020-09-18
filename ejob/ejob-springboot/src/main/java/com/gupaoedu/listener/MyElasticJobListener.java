package com.gupaoedu.listener;

import ch.qos.logback.core.util.TimeUtil;
import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

/**
 * @Author: qingshan
 * @Date: 2019/9/7 15:52
 * @Description: 咕泡学院，只为更好的你
 */
public class MyElasticJobListener implements ElasticJobListener {

    private long beginTime = 0;
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        beginTime = System.currentTimeMillis();

        System.out.println("===>{} JOB BEGIN TIME: {} <=== " + shardingContexts.getJobName() + beginTime);
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        long endTime = System.currentTimeMillis();
        System.out.println("===>{} JOB END TIME: {},TOTAL CAST: {} <===" + shardingContexts.getJobName() + endTime + (endTime - beginTime));
    }
}
