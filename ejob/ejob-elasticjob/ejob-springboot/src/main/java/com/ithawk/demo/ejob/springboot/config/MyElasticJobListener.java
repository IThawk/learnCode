package com.ithawk.demo.ejob.springboot.config;

import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.infra.listener.ElasticJobListener;
import org.apache.shardingsphere.elasticjob.infra.listener.ShardingContexts;

import javax.sql.DataSource;
import java.util.Objects;


public class MyElasticJobListener implements ElasticJobListener {

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {

    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {

    }

    @Override
    public String getType() {
        return "myElasticJobListener";
    }
}
