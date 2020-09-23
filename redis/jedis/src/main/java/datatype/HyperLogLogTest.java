package datatype;

import redis.clients.jedis.Jedis;

/**
 * @Author: qingshan
 * @Date: 2019/9/26 15:22
 * @Description: 咕泡学院，只为更好的你
 * pfadd,  pfcount,  pfmerge
 */
public class HyperLogLogTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.8.202", 6379);

        float size = 10000;

        for (int i = 0; i < size; i++) {
            jedis.pfadd("hll", "hll-" + i);
        }
        long total = jedis.pfcount("hll");
        System.out.println(String.format("统计个数: %s", total));
        System.out.println(String.format("正确率: %s", (total / size)));
        System.out.println(String.format("误差率: %s", 1 - (total / size)));
        jedis.close();
    }
}
