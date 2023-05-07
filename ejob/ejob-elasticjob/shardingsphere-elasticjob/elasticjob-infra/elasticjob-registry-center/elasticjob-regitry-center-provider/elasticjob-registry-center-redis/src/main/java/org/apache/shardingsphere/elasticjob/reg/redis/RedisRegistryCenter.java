/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.elasticjob.reg.redis;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.TransactionOp;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
//import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.base.LeaderExecutionCallback;
import org.apache.shardingsphere.elasticjob.reg.base.transaction.TransactionOperation;
import org.apache.shardingsphere.elasticjob.reg.exception.RegException;
import org.apache.shardingsphere.elasticjob.reg.exception.RegExceptionHandler;
import org.apache.shardingsphere.elasticjob.reg.listener.ConnectionStateChangedEventListener;
import org.apache.shardingsphere.elasticjob.reg.listener.ConnectionStateChangedEventListener.State;
import org.apache.shardingsphere.elasticjob.reg.listener.DataChangedEvent;
import org.apache.shardingsphere.elasticjob.reg.listener.DataChangedEvent.Type;
import org.apache.shardingsphere.elasticjob.reg.listener.DataChangedEventListener;
import org.apache.shardingsphere.elasticjob.reg.redis.exception.KeeperException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.client.codec.StringCodec;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * Registry center of ZooKeeper.
 */
@Slf4j
public final class RedisRegistryCenter implements CoordinatorRegistryCenter {
    
    @Getter(AccessLevel.PROTECTED)
    private final RedisConfiguration redisConfiguration;
    
    private final Map<String, CuratorCache> caches = new ConcurrentHashMap<>();
    
    @Getter
    private RedissonClient redissonClient;

//    private CuratorFramework client;
    
    public RedisRegistryCenter(final RedisConfiguration redisConfiguration) {
        this.redisConfiguration = redisConfiguration;
    }
    
    @Override
    public void init() {
        log.debug("Elastic job: zookeeper registry center init, server lists is: {}.", redisConfiguration.getServerLists());
//        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
//                .connectString(redisConfiguration.getServerLists())
//                .retryPolicy(new ExponentialBackoffRetry(redisConfiguration.getBaseSleepTimeMilliseconds(),
//                        redisConfiguration.getMaxRetries(), redisConfiguration.getMaxSleepTimeMilliseconds()))
//                .namespace(redisConfiguration.getNamespace());
//        if (0 != redisConfiguration.getSessionTimeoutMilliseconds()) {
//            builder.sessionTimeoutMs(redisConfiguration.getSessionTimeoutMilliseconds());
//        }
//        if (0 != redisConfiguration.getConnectionTimeoutMilliseconds()) {
//            builder.connectionTimeoutMs(redisConfiguration.getConnectionTimeoutMilliseconds());
//        }
//        if (!Strings.isNullOrEmpty(redisConfiguration.getDigest())) {
//            builder.authorization("digest", redisConfiguration.getDigest().getBytes(StandardCharsets.UTF_8))
//                    .aclProvider(new ACLProvider() {
//
//                        @Override
//                        public List<ACL> getDefaultAcl() {
//                            return ZooDefs.Ids.CREATOR_ALL_ACL;
//                        }
//
//                        @Override
//                        public List<ACL> getAclForPath(final String path) {
//                            return ZooDefs.Ids.CREATOR_ALL_ACL;
//                        }
//                    });
//        }
//        client = builder.build();
        //TODO
        redisConfiguration.useSingleServer().setAddress("redis://"+redisConfiguration.getServerLists());
        redissonClient = Redisson.create(redisConfiguration);
        try {
            if (redissonClient.isShuttingDown()) {
//                client.close();
                throw new KeeperException.OperationTimeoutException();
            }
            //CHECKSTYLE:OFF
        } catch (final Exception ex) {
            //CHECKSTYLE:ON
            RegExceptionHandler.handleException(ex);
        }
    }
    
    @Override
    public void close() {
//        for (Entry<String, CuratorCache> each : caches.entrySet()) {
//            each.getValue().close();
//        }
//        waitForCacheClose();
//        CloseableUtils.closeQuietly(redissonClient);
    }
    
    /*
     *  // TODO
     * sleep 500ms, let cache client close first and then client, otherwise will throw exception
     * reference：https://issues.apache.org/jira/browse/CURATOR-157
     */
    private void waitForCacheClose() {
        try {
            Thread.sleep(500L);
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    
    @Override
    public String get(final String key) {
        RMap<String,String>  cache = findCuratorCache(key);
        if (cache==null||cache.isEmpty()) {
            return getDirectly(key);
        }
        return cache.get(key);
    }
    
    private RMap<String,String> findCuratorCache(final String key) {
        return redissonClient.getMap(key, StringCodec.INSTANCE);
    }
    
    @Override
    public String getDirectly(final String key) {
        try {
            RMap<String,String>  cache = findCuratorCache(key);
            if (cache==null||cache.isEmpty()) {
                return key.substring(key.lastIndexOf('/')+1);
            }
//            redissonClient.getMap(key).put(key,key);
            return cache.get(key);
        //CHECKSTYLE:OFF
        } catch (final Exception ex) {
        //CHECKSTYLE:ON
            RegExceptionHandler.handleException(ex);
            return null;
        }
    }
    
    @Override
    public List<String> getChildrenKeys(final String key) {
        try {
//            String mKey = key.substring(key.lastIndexOf('/'));
            RMap<String,String> map =    redissonClient.getMap(key, StringCodec.INSTANCE);
            return map.keySet().stream()
                    .filter(v->!key.equals(v))
                    .map(v-> v.substring(v.lastIndexOf('/')+1))
                    .collect(Collectors.toList());
//            List<String> result = client.getChildren().forPath(key);
//            result.sort(Comparator.reverseOrder());
//            return result;
         //CHECKSTYLE:OFF
        } catch (final Exception ex) {
        //CHECKSTYLE:ON
            RegExceptionHandler.handleException(ex);
            return Collections.emptyList();
        }
    }
    
    @Override
    public int getNumChildren(final String key) {
        try {
            RMap<String,String> cache = redissonClient.getMap(key, StringCodec.INSTANCE);
//            Stat stat = client.checkExists().forPath(key);
            if (null != cache) {
                return cache.size()-1;
            }
            //CHECKSTYLE:OFF
        } catch (final Exception ex) {
            //CHECKSTYLE:ON
            RegExceptionHandler.handleException(ex);
        }
        return 0;
    }

    @Override
    public boolean isExisted(final String key) {
        try {
            return null != redissonClient.getMap(key, StringCodec.INSTANCE)&&redissonClient.getMap(key, StringCodec.INSTANCE).isExists();
        //CHECKSTYLE:OFF
        } catch (final Exception ex) {
        //CHECKSTYLE:ON
            RegExceptionHandler.handleException(ex);
            return false;
        }
    }
    
    @Override
    public void persist(final String key, final String value) {
        try {
            if (!isExisted(key)) {
                String mKey = key.substring(0,key.lastIndexOf('/'));
                redissonClient.getMap(key, StringCodec.INSTANCE).put(key,value);
                redissonClient.getMap(mKey, StringCodec.INSTANCE).put(key,value);
            } else {
                update(key, value);
            }
        //CHECKSTYLE:OFF
        } catch (final Exception ex) {
        //CHECKSTYLE:ON
            RegExceptionHandler.handleException(ex);
        }
    }
    
    @Override
    public void update(final String key, final String value) {
        try {
            String mKey = key.substring(0,key.lastIndexOf('/'));
            redissonClient.getMap(key, StringCodec.INSTANCE).put(key,value);
            redissonClient.getMap(mKey, StringCodec.INSTANCE).put(key,value);
            //CHECKSTYLE:OFF
        } catch (final Exception ex) {
        //CHECKSTYLE:ON
            RegExceptionHandler.handleException(ex);
        }
    }

    /**
     * 持久化临时数据
     * @param key key
     * @param value value
     */
    @Override
    public void persistEphemeral(final String key, final String value) {
        try {
            update(key,value);
            //CHECKSTYLE:OFF
        } catch (final Exception ex) {
        //CHECKSTYLE:ON
            RegExceptionHandler.handleException(ex);
        }
    }
    
    @Override
    public String persistSequential(final String key, final String value) {
        try {
            update(key, value);
            //CHECKSTYLE:OFF
        } catch (final Exception ex) {
        //CHECKSTYLE:ON
            RegExceptionHandler.handleException(ex);
        }
        return null;
    }
    
    @Override
    public void persistEphemeralSequential(final String key) {
        try {
            redissonClient.getBucket(key, StringCodec.INSTANCE).deleteAsync().await(3000);
        //CHECKSTYLE:OFF
        } catch (final Exception ex) {
        //CHECKSTYLE:ON
            RegExceptionHandler.handleException(ex);
        }
    }
    
    @Override
    public void remove(final String key) {
        try {
            redissonClient.getBucket(key, StringCodec.INSTANCE).deleteAsync().await(3000);
        //CHECKSTYLE:OFF
        } catch (final Exception ex) {
        //CHECKSTYLE:ON
            RegExceptionHandler.handleException(ex);
        }
    }
    
    @Override
    public long getRegistryCenterTime(final String key) {
        long result = 0L;
        try {
            return 1000L;
        //CHECKSTYLE:OFF
        } catch (final Exception ex) {
        //CHECKSTYLE:ON
            RegExceptionHandler.handleException(ex);
        }
        Preconditions.checkState(0L != result, "Cannot get registry center time.");
        return result;
    }
    
    @Override
    public Object getRawClient() {
        return redissonClient;
    }
    
    @Override
    public void addConnectionStateChangedEventListener(final ConnectionStateChangedEventListener listener) {
//        CoordinatorRegistryCenter coordinatorRegistryCenter = this;
//        client.getConnectionStateListenable().addListener((client, newState) -> {
//            State state;
//            switch (newState) {
//                case CONNECTED:
//                    state = State.CONNECTED;
//                    break;
//                case LOST:
//                case SUSPENDED:
//                    state = State.UNAVAILABLE;
//                    break;
//                case RECONNECTED:
//                    state = State.RECONNECTED;
//                    break;
//                case READ_ONLY:
//                default:
//                    throw new IllegalStateException("Illegal registry center connection state: " + newState);
//            }
//            listener.onStateChanged(coordinatorRegistryCenter, state);
//        });
    }
    
    @Override
    public void executeInTransaction(final List<TransactionOperation> transactionOperations) throws Exception {
//        client.transaction().forOperations(toCuratorOps(transactionOperations));
    }
    
//    private List<CuratorOp> toCuratorOps(final List<TransactionOperation> transactionOperations) {
//        List<CuratorOp> result = new ArrayList<>(transactionOperations.size());
//        TransactionOp transactionOp = client.transactionOp();
//        for (TransactionOperation each : transactionOperations) {
//            result.add(toCuratorOp(each, transactionOp));
//        }
//        return result;
//    }
    
    private CuratorOp toCuratorOp(final TransactionOperation each, final TransactionOp transactionOp) {
        try {
            switch (each.getType()) {
                case CHECK_EXISTS:
                    return transactionOp.check().forPath(each.getKey());
                case ADD:
                    return transactionOp.create().forPath(each.getKey(), each.getValue().getBytes(StandardCharsets.UTF_8));
                case UPDATE:
                    return transactionOp.setData().forPath(each.getKey(), each.getValue().getBytes(StandardCharsets.UTF_8));
                case DELETE:
                    return transactionOp.delete().forPath(each.getKey());
                default:
                    throw new UnsupportedOperationException(each.toString());
            }
            //CHECKSTYLE:OFF
        } catch (final Exception ex) {
            //CHECKSTYLE:ON
            throw new RegException(ex);
        }
    }
    
    @Override
    public void addCacheData(final String cachePath) {
//        redissonClient.getMap(cachePath, StringCodec.INSTANCE).put(cachePath,cachePath);
//        CuratorCache cache = CuratorCache.build(client, cachePath);
//        try {
//            cache.start();
//        //CHECKSTYLE:OFF
//        } catch (final Exception ex) {
//        //CHECKSTYLE:ON
//            RegExceptionHandler.handleException(ex);
//        }
//        caches.put(cachePath + "/", cache);
    }
    
    @Override
    public void evictCacheData(final String cachePath) {
        redissonClient.getMap(cachePath, StringCodec.INSTANCE).remove(cachePath);
//        if (null != cache) {
//            cache.close();
//        }
    }
    
    @Override
    public Object getRawCache(final String cachePath) {
        return  redissonClient.getMapCache(cachePath).get(cachePath);
    }
    
    @Override
    public void executeInLeader(final String key, final LeaderExecutionCallback callback) {
        try {
            LeaderLatch latch = new LeaderLatch(redissonClient, key);
            latch.tryHold(key);
        }catch (Exception e){
            //        latch.start();
//        latch.await();
            log.error("executeInLeader error :{}", ExceptionUtils.getFullStackTrace(e));
//            callback.execute();
        }


    }
    
    @Override
    public void watch(final String key, final DataChangedEventListener listener, final Executor executor) {
//        CuratorCache cache = caches.get(key + "/");
        RMap<String,String> cache1= redissonClient.getMap(key, StringCodec.INSTANCE);
        CuratorCacheListener cacheListener = (curatorType, oldData, newData) -> {
            if (null == newData && null == oldData) {
                return;
            }
            Type type = getTypeFromCuratorType(curatorType);
            String path = Type.DELETED == type ? oldData.getPath() : newData.getPath();
            if (path.isEmpty() || Type.IGNORED == type) {
                return;
            }
            byte[] data = Type.DELETED == type ? oldData.getData() : newData.getData();
            listener.onChange(new DataChangedEvent(type, path, null == data ? "" : new String(data, StandardCharsets.UTF_8)));
        };
        if (executor != null) {
//            cache.listenable().addListener(cacheListener, executor);
        } else {
//            cache.listenable().addListener(cacheListener);
        }
    }
    
    private Type getTypeFromCuratorType(final CuratorCacheListener.Type curatorType) {
        switch (curatorType) {
            case NODE_CREATED:
                return Type.ADDED;
            case NODE_DELETED:
                return Type.DELETED;
            case NODE_CHANGED:
                return Type.UPDATED;
            default:
                return Type.IGNORED;
        }
    }
    
    private void handleException(final Exception ex) {
        if (ex instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        } else {
            throw new RegException(ex);
        }
    }
}
