//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.shardingsphere.elasticjob.reg.redis;

import org.apache.shardingsphere.elasticjob.reg.redis.exception.CancelLeadershipException;
import org.redisson.api.RedissonClient;

public abstract class LeaderSelectorListenerAdapter implements LeaderSelectorListener {

    RedissonClient redissonClient;
    public LeaderSelectorListenerAdapter(RedissonClient redissonClient ) {
        this.redissonClient= redissonClient;
    }

    public void stateChanged(RedissonClient redissonClient, ConnectionState newState) {
        if (redissonClient.isShutdown()) {
            throw new CancelLeadershipException();
        }
    }
}
