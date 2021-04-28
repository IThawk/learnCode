package com.ithawk.learn.springboot.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-04-11 23:49
 */
public class Job implements SimpleJob {
    Logger logger = LoggerFactory.getLogger(Job.class);

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info(String.format("Thread ID: %s, 作业分片总数: %s, " +
                "当前分片项: %s.当前参数: %s," +
                "作业名称: %s.作业自定义参数: %s"
            ,
            Thread.currentThread().getId(),
            shardingContext.getShardingTotalCount(),
            shardingContext.getShardingItem(),
            shardingContext.getShardingParameter(),
            shardingContext.getJobName(),
            shardingContext.getJobParameter()
        ));

    }
}
