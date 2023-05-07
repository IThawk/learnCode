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

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.test.KillSession;
import org.apache.shardingsphere.elasticjob.reg.base.ElectionCandidate;
import org.apache.shardingsphere.elasticjob.reg.redis.fixture.EmbedTestingServer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RedisrElectionServiceTest {
    
    private static final String HOST_AND_PORT = "localhost:6379";
    
    private static final String ELECTION_PATH = "/election";
    
    @Mock
    private ElectionCandidate electionCandidate;
    
    @BeforeClass
    public static void init() {
        EmbedTestingServer.start();
    }
    
    @Test
    public void assertContend() throws Exception {
        RedissonClient redissonClient = Redisson.create();
        RedisElectionService service = new RedisElectionService(HOST_AND_PORT, redissonClient, ELECTION_PATH, electionCandidate);
        service.start();
//        ElectionCandidate anotherElectionCandidate = mock(ElectionCandidate.class);
//        CuratorFramework anotherClient = CuratorFrameworkFactory.newClient(EmbedTestingServer.getConnectionString(), new RetryOneTime(2000));
//        RedisElectionService anotherService = new RedisElectionService("ANOTHER_CLIENT:8899", anotherClient, ELECTION_PATH, anotherElectionCandidate);
//        anotherClient.start();
//        anotherClient.blockUntilConnected();
//        anotherService.start();
//        KillSession.kill(client.getZookeeperClient().getZooKeeper());
//        service.stop();
//        blockUntilCondition(() -> hasLeadership(anotherService));
//        ((CountDownLatch) getFieldValue(anotherService, "leaderLatch")).countDown();
//        blockUntilCondition(() -> !hasLeadership(anotherService));
//        anotherService.stop();
//        verify(anotherElectionCandidate, atLeastOnce()).startLeadership();
//        verify(anotherElectionCandidate, atLeastOnce()).stopLeadership();
    }
    
    @SneakyThrows
    private void blockUntilCondition(final Supplier<Boolean> condition) {
        while (!condition.get()) {
            Thread.sleep(100);
        }
    }

    @SneakyThrows
    private boolean hasLeadership(final RedisElectionService zookeeperElectionService) {
        return ((LeaderSelector) getFieldValue(zookeeperElectionService, "leaderSelector")).hasLeadership();
    }

    @SneakyThrows
    private Object getFieldValue(final Object target, final String fieldName) {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }
}
