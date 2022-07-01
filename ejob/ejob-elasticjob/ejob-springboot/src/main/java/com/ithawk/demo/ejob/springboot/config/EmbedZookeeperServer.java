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

package com.ithawk.demo.ejob.springboot.config;

import com.ithawk.demo.ejob.springboot.utils.KillServerUtils;
import org.apache.curator.test.TestingServer;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.client.ZKClientConfig;

import java.io.File;
import java.io.IOException;

/**
 * Embed ZooKeeper.
 * 
 * <p>
 *     Only used for examples
 * </p>
 */
public final class EmbedZookeeperServer {
    
    private static TestingServer testingServer;

    public ZooKeeper newZooKeeper(String connectString, int sessionTimeout, Watcher watcher, boolean canBeReadOnly)
            throws Exception {
        ZKClientConfig config = new ZKClientConfig();
        config.setProperty(ZKClientConfig.ENABLE_CLIENT_SASL_KEY, "false");

        return new ZooKeeper(connectString, sessionTimeout, watcher, canBeReadOnly, config);
    }
    
    /**
     * Embed ZooKeeper.
     * 
     * @param port ZooKeeper port
     */
    public static void start(final int port) {
        try {
//            KillServerUtils.startKillPort(4181);
            testingServer = new TestingServer(port, new File(String.format("target/test_zk_data/%s/", System.nanoTime())));
        // CHECKSTYLE:OFF
        } catch (final Exception ex) {
        // CHECKSTYLE:ON
            ex.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Thread.sleep(1000L);
                    testingServer.close();
                } catch (final InterruptedException | IOException ignore) {
                }
            }));
        }
    }


    public static void stop() {
        if(testingServer==null){
            return;
        }
        try {

            testingServer.close();
            // CHECKSTYLE:OFF
        } catch (final Exception ex) {
            // CHECKSTYLE:ON
            ex.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Thread.sleep(1000L);
                    testingServer.close();
                } catch (final InterruptedException | IOException ignore) {
                }
            }));
        }
    }
}
