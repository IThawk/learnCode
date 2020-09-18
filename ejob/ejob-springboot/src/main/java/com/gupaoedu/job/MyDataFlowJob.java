package com.gupaoedu.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Author: qingshan
 * @Date: 2019/10/25 15:29
 * @Description: 咕泡学院，只为更好的你
 */
@Component
public class MyDataFlowJob implements DataflowJob<String> {


    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        System.out.println(String.format("------【获取数据】Thread ID: %s, %s,任务总片数: %s, " +
                        "当前分片项: %s.当前参数: %s," +
                        "当前任务名称: %s.当前任务参数 %s",
                Thread.currentThread().getId(),
                new SimpleDateFormat("HH:mm:ss").format(new Date()),
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem(),
                shardingContext.getShardingParameter(),
                shardingContext.getJobName(),
                shardingContext.getJobParameter()
        ));
        // 假装从文件或者数据库中获取到了数据
        Random random = new Random();
        if( random.nextInt() % 3 != 0 ){
            return null;
        }
        return Arrays.asList("qingshan","jack","seven");
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> data) {
        System.out.println(String.format("------【处理数据】Thread ID: %s, %s,任务总片数: %s, " +
                        "当前分片项: %s.当前参数: %s," +
                        "当前任务名称: %s.当前任务参数 %s",
                Thread.currentThread().getId(),
                new SimpleDateFormat("HH:mm:ss").format(new Date()),
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem(),
                shardingContext.getShardingParameter(),
                shardingContext.getJobName(),
                shardingContext.getJobParameter()
        ));
        // 处理完数据要移除掉，不然就会一直跑
        data.forEach(x-> System.out.println("开始处理数据："+x));
    }
}
