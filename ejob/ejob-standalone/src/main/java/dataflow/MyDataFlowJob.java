package dataflow;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @Author: qingshan
 * @Date: 2019/10/25 15:29
 * @Description: 咕泡学院，只为更好的你
 */
public class MyDataFlowJob implements DataflowJob<String> {
    public List<String> fetchData(ShardingContext shardingContext) {
        System.out.println("开始获取数据");
        // 假装从文件或者数据库中获取到了数据
        Random random = new Random();
/*        if( random.nextInt() % 3 != 0 ){
            return null;
        }*/
        return Arrays.asList("qingshan","jack","seven");
    }

    public void processData(ShardingContext shardingContext, List<String> data) {
        for(String val : data){
            // 处理完数据要移除掉，不然就会一直跑
            System.out.println("开始处理数据："+val);
        }

    }
}
