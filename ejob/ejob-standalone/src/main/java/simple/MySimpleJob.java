package simple;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: qingshan
 * @Date: 2019/9/6 14:46
 * @Description: 咕泡学院，只为更好的你
 */
public class MySimpleJob implements SimpleJob {
    public void execute(ShardingContext context) {
        System.out.println(String.format("分片项 ShardingItem: %s | 运行时间: %s | 线程ID: %s | 分片参数: %s ",
                context.getShardingItem(), new SimpleDateFormat("HH:mm:ss").format(new Date()),
                Thread.currentThread().getId(), context.getShardingParameter()));
    }
}