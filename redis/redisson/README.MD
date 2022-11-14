Redisson 使用redis解决分布式中的问题
## 引入依赖：
```xml
    <dependency>
        <groupId>org.redisson</groupId>
        <artifactId>redisson</artifactId>
        <version>3.14.1</version>
    </dependency>
```
## 单机版连接
```java
        import org.redisson.Redisson;
        import org.redisson.api.RedissonClient;
        import org.redisson.config.Config;
        
        public class RedissonClientUtils {
            public static RedissonClient getRedissonClient() {
                Config config = new Config();
                config.useSingleServer()
                        //可以用"rediss://"来启用SSL连接
                        .setAddress("redis://192.168.56.101:6379");
                RedissonClient redisson = Redisson.create(config);
                return redisson;
            }
        }

```