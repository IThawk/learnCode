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

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.shardingsphere.elasticjob.reg.redis.fixture.EmbedTestingServer;
import org.apache.shardingsphere.elasticjob.reg.redis.util.ZookeeperRegistryCenterTestUtil;
import org.apache.zookeeper.KeeperException.NoAuthException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class RedisRegistryCenterForAuthTest {
    
    private static final String NAME_SPACE = RedisRegistryCenterForAuthTest.class.getName();
    
    private static final RedisConfiguration ZOOKEEPER_CONFIGURATION = new RedisConfiguration(org.apache.shardingsphere.elasticjob.reg.redis.fixture.EmbedTestingServer.getConnectionString(), NAME_SPACE);
    
    private static RedisRegistryCenter zkRegCenter;
    
    @BeforeClass
    public static void setUp() {
        org.apache.shardingsphere.elasticjob.reg.redis.fixture.EmbedTestingServer.start();
        ZOOKEEPER_CONFIGURATION.setDigest("digest:password");
        ZOOKEEPER_CONFIGURATION.setSessionTimeoutMilliseconds(5000);
        ZOOKEEPER_CONFIGURATION.setConnectionTimeoutMilliseconds(5000);
        zkRegCenter = new RedisRegistryCenter(ZOOKEEPER_CONFIGURATION);
        zkRegCenter.init();
        ZookeeperRegistryCenterTestUtil.persist(zkRegCenter);
    }
    
    @AfterClass
    public static void tearDown() {
        zkRegCenter.close();
    }
    
    @Test
    public void assertInitWithDigestSuccess() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(org.apache.shardingsphere.elasticjob.reg.redis.fixture.EmbedTestingServer.getConnectionString())
            .retryPolicy(new RetryOneTime(2000))
            .authorization("digest", "digest:password".getBytes()).build();
        client.start();
        client.blockUntilConnected();
        assertThat(client.getData().forPath("/" + RedisRegistryCenterForAuthTest.class.getName() + "/test/deep/nested"), is("deepNested".getBytes()));
    }
    
    @Test(expected = NoAuthException.class)
    public void assertInitWithDigestFailure() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(EmbedTestingServer.getConnectionString(), new RetryOneTime(2000));
        client.start();
        client.blockUntilConnected();
        client.getData().forPath("/" + RedisRegistryCenterForAuthTest.class.getName() + "/test/deep/nested");
    }
}
