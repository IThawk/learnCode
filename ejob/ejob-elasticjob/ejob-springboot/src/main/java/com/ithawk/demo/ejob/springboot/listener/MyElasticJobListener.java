package com.ithawk.demo.ejob.springboot.listener;

import ch.qos.logback.core.util.TimeUtil;
import org.apache.shardingsphere.elasticjob.infra.listener.ElasticJobListener;
import org.apache.shardingsphere.elasticjob.infra.listener.ShardingContexts;
import org.apache.shardingsphere.elasticjob.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import org.apache.shardingsphere.elasticjob.infra.listener.ElasticJobListenerFactory;

/**
 * @Author: IThawk
 */
public class MyElasticJobListener extends AbstractDistributeOnceElasticJobListener {


    public MyElasticJobListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
    }

    @Override
    public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {

    }

    @Override
    public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {

    }

    @Override
    public String getType() {
        return "myElasticJobListener";
    }
}
