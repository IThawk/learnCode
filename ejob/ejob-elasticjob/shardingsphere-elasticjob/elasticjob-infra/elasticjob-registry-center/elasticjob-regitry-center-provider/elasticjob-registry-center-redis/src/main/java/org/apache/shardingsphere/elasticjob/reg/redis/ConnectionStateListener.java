//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.shardingsphere.elasticjob.reg.redis;


import org.redisson.api.RedissonClient;

public interface ConnectionStateListener {
    void stateChanged(RedissonClient var1, ConnectionState var2);

    default boolean doNotProxy() {
        return false;
    }
}
