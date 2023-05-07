//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.shardingsphere.elasticjob.reg.redis;
import org.redisson.api.RedissonClient;

public interface LeaderSelectorListener extends ConnectionStateListener {
    void takeLeadership(RedissonClient redissonClient) throws Exception;
}
