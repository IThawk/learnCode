package datatype;

import redis.clients.jedis.Jedis;

/**
 * @Author: qingshan
 * @Date: 2019/9/26 17:58
 * @Description: 咕泡学院，只为更好的你
 */
public class BasicTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("qingshan", "2673");
        System.out.println(jedis.get("qingshan"));
        jedis.close();
    }
}
