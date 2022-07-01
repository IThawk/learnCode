package com.ithawk.demo.ejob.springboot.listener;

import ch.qos.logback.core.util.TimeUtil;
import org.apache.shardingsphere.elasticjob.infra.listener.ElasticJobListener;
import org.apache.shardingsphere.elasticjob.infra.listener.ShardingContexts;


/**
 * @Author: IThawk
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

    @Override
    public String getType() {
        return "MyElasticJobListener";
    }
}
