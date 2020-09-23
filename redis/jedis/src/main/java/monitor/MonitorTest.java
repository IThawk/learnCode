package monitor;

import com.google.common.util.concurrent.AtomicLongMap;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisMonitor;

import java.util.List;

/**
 * @Author: qingshan
 * @Date: 2019/9/28 21:36
 * @Description: 咕泡学院，只为更好的你
 */
public class MonitorTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //获取10万条命令
        jedis.monitor(new JedisMonitor() {
            @Override
            public void onCommand(String command) {
                System.out.println("#monitor: " + command);
                AtomicLongMap<String> ATOMIC_LONG_MAP = AtomicLongMap.create();
                // ATOMIC_LONG_MAP.incrementAndGet(command);
            }
        });

    }
}
