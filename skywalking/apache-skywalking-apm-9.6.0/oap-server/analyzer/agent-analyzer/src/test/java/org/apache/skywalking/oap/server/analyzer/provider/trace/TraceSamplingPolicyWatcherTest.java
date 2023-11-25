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
 *
 */

package org.apache.skywalking.oap.server.analyzer.provider.trace;

import org.apache.skywalking.oap.server.analyzer.provider.AnalyzerModuleConfig;
import org.apache.skywalking.oap.server.analyzer.provider.AnalyzerModuleProvider;
import org.apache.skywalking.oap.server.analyzer.provider.trace.sampling.SamplingPolicy;
import org.apache.skywalking.oap.server.analyzer.provider.trace.sampling.SamplingPolicySettings;
import org.apache.skywalking.oap.server.configuration.api.ConfigChangeWatcher;
import org.apache.skywalking.oap.server.configuration.api.ConfigTable;
import org.apache.skywalking.oap.server.configuration.api.ConfigWatcherRegister;
import org.apache.skywalking.oap.server.configuration.api.FetchingConfigWatcherRegister;
import org.apache.skywalking.oap.server.configuration.api.GroupConfigTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@ExtendWith(MockitoExtension.class)
public class TraceSamplingPolicyWatcherTest {

    private AnalyzerModuleProvider provider;
    private AnalyzerModuleConfig moduleConfig;

    @BeforeEach
    public void init() {
        provider = new AnalyzerModuleProvider();
        moduleConfig = new AnalyzerModuleConfig();
        moduleConfig.setTraceSamplingPolicySettingsFile("trace-sampling-policy-settings.yml");
    }

    @Test
    public void testStaticConfigInit() {
        TraceSamplingPolicyWatcher watcher = new TraceSamplingPolicyWatcher(moduleConfig, provider);
        // default sample = 10000
        globalDefaultSamplingRateEquals(watcher, 9999);
    }

    @Test
    @Timeout(20)
    public void testTraceLatencyThresholdDynamicUpdate() throws InterruptedException {
        ConfigWatcherRegister register = new TraceLatencyThresholdMockConfigWatcherRegister(3);

        TraceSamplingPolicyWatcher watcher = new TraceSamplingPolicyWatcher(moduleConfig, provider);
        register.registerConfigChangeWatcher(watcher);
        register.start();
        // Default duration is -1, so 3000 must not be sampled,until updating to 3000
        while (!watcher.shouldSample("", 10000, 3000)) {
            Thread.sleep(2000);
        }
        Assertions.assertTrue(watcher.shouldSample("", 10000, 3001));
    }

    @Test
    public void testTraceLatencyThresholdNotify() {
        TraceSamplingPolicyWatcher watcher = new TraceSamplingPolicyWatcher(moduleConfig, provider);
        ConfigChangeWatcher.ConfigChangeEvent value1 = new ConfigChangeWatcher.ConfigChangeEvent(
            "default:\n" +
                "  duration: 8000", ConfigChangeWatcher.EventType.MODIFY);

        watcher.notify(value1);
        globalDefaultDurationEquals(watcher, 8000);
        Assertions.assertEquals(watcher.value(), "default:\n" +
            "  duration: 8000");

        ConfigChangeWatcher.ConfigChangeEvent value2 = new ConfigChangeWatcher.ConfigChangeEvent(
            "default:\n" +
                "  duration: 8000", ConfigChangeWatcher.EventType.DELETE);

        watcher.notify(value2);
        Assertions.assertEquals(watcher.value(), null);

        ConfigChangeWatcher.ConfigChangeEvent value3 = new ConfigChangeWatcher.ConfigChangeEvent(
            "default:\n" +
                "  duration: 800", ConfigChangeWatcher.EventType.ADD);

        watcher.notify(value3);
        globalDefaultDurationEquals(watcher, 800);
        Assertions.assertEquals(watcher.value(), "default:\n" +
            "  duration: 800");

        ConfigChangeWatcher.ConfigChangeEvent value4 = new ConfigChangeWatcher.ConfigChangeEvent(
            "default:\n" +
                "  duration: abc", ConfigChangeWatcher.EventType.MODIFY);

        watcher.notify(value4);
        globalDefaultDurationEquals(watcher, 800);
        Assertions.assertEquals(watcher.value(), "default:\n" +
            "  duration: 800");

        ConfigChangeWatcher.ConfigChangeEvent value5 = new ConfigChangeWatcher.ConfigChangeEvent(
            "default:\n" +
                "  rate: abc\n" +
                "  duration: 900", ConfigChangeWatcher.EventType.MODIFY);

        watcher.notify(value5);
        globalDefaultDurationEquals(watcher, 800);
        Assertions.assertEquals(watcher.value(), "default:\n" +
            "  duration: 800");
    }

    public static class TraceLatencyThresholdMockConfigWatcherRegister extends FetchingConfigWatcherRegister {

        public TraceLatencyThresholdMockConfigWatcherRegister(long syncPeriod) {
            super(syncPeriod);
        }

        @Override
        public Optional<ConfigTable> readConfig(Set<String> keys) {
            ConfigTable table = new ConfigTable();
            table.add(new ConfigTable.ConfigItem("agent-analyzer.default.traceSamplingPolicy", "default:\n" +
                "  duration: 3000"));
            return Optional.of(table);
        }

        @Override
        public Optional<GroupConfigTable> readGroupConfig(final Set<String> keys) {
            return Optional.empty();
        }
    }

    @Test
    @Timeout(20)
    public void testDefaultSampleRateDynamicUpdate() throws InterruptedException {
        ConfigWatcherRegister register = new DefaultSampleRateMockConfigWatcherRegister(3);

        TraceSamplingPolicyWatcher watcher = new TraceSamplingPolicyWatcher(moduleConfig, provider);
        register.registerConfigChangeWatcher(watcher);
        register.start();
        // Default is 10000, so 9000 must be sampled,until updating to 9000
        while (watcher.shouldSample("", 9000, -1)) {
            Thread.sleep(2000);
        }
        globalDefaultSamplingRateEquals(watcher, 8999);
    }

    @Test
    public void testDefaultSampleRateNotify() {
        TraceSamplingPolicyWatcher watcher = new TraceSamplingPolicyWatcher(moduleConfig, provider);
        ConfigChangeWatcher.ConfigChangeEvent value1 = new ConfigChangeWatcher.ConfigChangeEvent(
            "default:\n" +
                "  rate: 8000", ConfigChangeWatcher.EventType.MODIFY);

        watcher.notify(value1);
        globalDefaultSamplingRateEquals(watcher, 7999);
        Assertions.assertEquals(watcher.value(), "default:\n" +
            "  rate: 8000");

        ConfigChangeWatcher.ConfigChangeEvent value2 = new ConfigChangeWatcher.ConfigChangeEvent(
            "default:\n" +
                "  rate: 1000", ConfigChangeWatcher.EventType.DELETE);

        watcher.notify(value2);
        globalDefaultSamplingRateEquals(watcher, 9999);
        Assertions.assertEquals(watcher.value(), null);

        ConfigChangeWatcher.ConfigChangeEvent value3 = new ConfigChangeWatcher.ConfigChangeEvent(
            "default:\n" +
                "  rate: 500", ConfigChangeWatcher.EventType.ADD);

        watcher.notify(value3);
        globalDefaultSamplingRateEquals(watcher, 499);
        Assertions.assertEquals(watcher.value(), "default:\n" +
            "  rate: 500");

        ConfigChangeWatcher.ConfigChangeEvent value4 = new ConfigChangeWatcher.ConfigChangeEvent(
            "default:\n" +
                "  rate: abc", ConfigChangeWatcher.EventType.MODIFY);

        watcher.notify(value4);
        globalDefaultSamplingRateEquals(watcher, 499);
        Assertions.assertEquals(watcher.value(), "default:\n" +
            "  rate: 500");

        ConfigChangeWatcher.ConfigChangeEvent value5 = new ConfigChangeWatcher.ConfigChangeEvent(
            "default:\n" +
                "  rate: 400" +
                "  duration: abc", ConfigChangeWatcher.EventType.MODIFY);

        watcher.notify(value5);
        globalDefaultSamplingRateEquals(watcher, 499);
        Assertions.assertEquals(watcher.value(), "default:\n" +
            "  rate: 500");
    }

    public static class DefaultSampleRateMockConfigWatcherRegister extends FetchingConfigWatcherRegister {

        public DefaultSampleRateMockConfigWatcherRegister(long syncPeriod) {
            super(syncPeriod);
        }

        @Override
        public Optional<ConfigTable> readConfig(Set<String> keys) {
            ConfigTable table = new ConfigTable();
            table.add(new ConfigTable.ConfigItem("agent-analyzer.default.traceSamplingPolicy", "default:\n" +
                "  rate: 9000"));
            return Optional.of(table);
        }

        @Override
        public Optional<GroupConfigTable> readGroupConfig(final Set<String> keys) {
            return Optional.empty();
        }
    }

    @Test
    @Timeout(20)
    public void testServiceSampleRateDynamicUpdate() throws InterruptedException {
        ConfigWatcherRegister register = new ServiceMockConfigWatcherRegister(3);

        TraceSamplingPolicyWatcher watcher = new TraceSamplingPolicyWatcher(moduleConfig, provider);
        Whitebox.setInternalState(provider, "moduleConfig", moduleConfig);
        provider.getModuleConfig().setTraceSamplingPolicyWatcher(watcher);
        register.registerConfigChangeWatcher(watcher);
        register.start();

        while (getSamplingPolicy("serverName1", watcher) == null) {
            Thread.sleep(1000);
        }

        SamplingPolicy samplingPolicy = getSamplingPolicy("serverName1", watcher);
        Assertions.assertEquals(samplingPolicy.getRate().intValue(), 2000);
        Assertions.assertEquals(samplingPolicy.getDuration().intValue(), 30000);
        Assertions.assertEquals(getSamplingPolicy("serverName1", provider.getModuleConfig().getTraceSamplingPolicyWatcher())
                                .getRate()
                                .intValue(), 2000);
    }

    @Test
    public void testServiceSampleRateNotify() {
        TraceSamplingPolicyWatcher watcher = new TraceSamplingPolicyWatcher(moduleConfig, provider);
        ConfigChangeWatcher.ConfigChangeEvent value1 = new ConfigChangeWatcher.ConfigChangeEvent(
            "services:\n" +
                "  - name: serverName1\n" +
                "    rate: 8000\n" +
                "    duration: 20000", ConfigChangeWatcher.EventType.MODIFY);

        watcher.notify(value1);

        Assertions.assertEquals(getSamplingPolicy("serverName1", watcher).getRate().intValue(), 8000);
        Assertions.assertEquals(getSamplingPolicy("serverName1", watcher).getDuration().intValue(), 20000);
        Assertions.assertEquals(watcher.value(), "services:\n" +
            "  - name: serverName1\n" +
            "    rate: 8000\n" +
            "    duration: 20000");

        // use serverName1's sampling rate
        Assertions.assertTrue(watcher.shouldSample("serverName1", 7999, -1));
        Assertions.assertTrue(watcher.shouldSample("serverName1", 10000, 20000));

        ConfigChangeWatcher.ConfigChangeEvent value2 = new ConfigChangeWatcher.ConfigChangeEvent(
            "", ConfigChangeWatcher.EventType.DELETE);

        watcher.notify(value2);

        Assertions.assertNull(getSamplingPolicy("serverName1", watcher));
        // use global sampling rate
        Assertions.assertTrue(watcher.shouldSample("serverName1", 9999, -1));
        Assertions.assertFalse(watcher.shouldSample("serverName1", 10000, 1));

        Assertions.assertEquals(watcher.value(), null);

        ConfigChangeWatcher.ConfigChangeEvent value3 = new ConfigChangeWatcher.ConfigChangeEvent(
            "services:\n" +
                "  - name: serverName1\n" +
                "    rate: 8000\n" +
                "    duration: 20000", ConfigChangeWatcher.EventType.ADD);

        watcher.notify(value3);
        Assertions.assertEquals(getSamplingPolicy("serverName1", watcher).getRate().intValue(), 8000);
        Assertions.assertEquals(getSamplingPolicy("serverName1", watcher).getDuration().intValue(), 20000);
        Assertions.assertTrue(watcher.shouldSample("serverName1", 7999, -1));
        Assertions.assertTrue(watcher.shouldSample("serverName1", 10000, 20000));

        Assertions.assertEquals(watcher.value(), "services:\n" +
            "  - name: serverName1\n" +
            "    rate: 8000\n" +
            "    duration: 20000");

        ConfigChangeWatcher.ConfigChangeEvent value4 = new ConfigChangeWatcher.ConfigChangeEvent(
            "services:\n" +
                "  - name: serverName1\n" +
                "    rate: 9000\n" +
                "    duration: 30000", ConfigChangeWatcher.EventType.MODIFY);

        watcher.notify(value4);
        Assertions.assertEquals(getSamplingPolicy("serverName1", watcher).getRate().intValue(), 9000);
        Assertions.assertEquals(getSamplingPolicy("serverName1", watcher).getDuration().intValue(), 30000);
        Assertions.assertTrue(watcher.shouldSample("serverName1", 8999, -1));
        Assertions.assertTrue(watcher.shouldSample("serverName1", 10000, 30000));

        Assertions.assertEquals(watcher.value(), "services:\n" +
            "  - name: serverName1\n" +
            "    rate: 9000\n" +
            "    duration: 30000");

        ConfigChangeWatcher.ConfigChangeEvent value5 = new ConfigChangeWatcher.ConfigChangeEvent(
            "services:\n" +
                "  - name: serverName1\n" +
                "    rate: 8000\n" +
                "    duration: abc", ConfigChangeWatcher.EventType.MODIFY);

        watcher.notify(value5);
        Assertions.assertEquals(getSamplingPolicy("serverName1", watcher).getRate().intValue(), 9000);
        Assertions.assertEquals(getSamplingPolicy("serverName1", watcher).getDuration().intValue(), 30000);
        Assertions.assertTrue(watcher.shouldSample("serverName1", 8999, -1));
        Assertions.assertTrue(watcher.shouldSample("serverName1", 10000, 30000));

        Assertions.assertEquals(watcher.value(), "services:\n" +
            "  - name: serverName1\n" +
            "    rate: 9000\n" +
            "    duration: 30000");

    }

    public static class ServiceMockConfigWatcherRegister extends FetchingConfigWatcherRegister {

        public ServiceMockConfigWatcherRegister(long syncPeriod) {
            super(syncPeriod);
        }

        @Override
        public Optional<ConfigTable> readConfig(Set<String> keys) {
            ConfigTable table = new ConfigTable();
            table.add(new ConfigTable.ConfigItem("agent-analyzer.default.traceSamplingPolicy", "services:\n" +
                "  - name: serverName1\n" +
                "    rate: 2000\n" +
                "    duration: 30000"));
            return Optional.of(table);
        }

        @Override
        public Optional<GroupConfigTable> readGroupConfig(final Set<String> keys) {
            return Optional.empty();
        }
    }

    private void globalDefaultSamplingRateEquals(TraceSamplingPolicyWatcher watcher, int sample) {
        Assertions.assertTrue(watcher.shouldSample("", sample, -1));
        Assertions.assertFalse(watcher.shouldSample("", sample + 1, -1));
    }

    private void globalDefaultDurationEquals(TraceSamplingPolicyWatcher watcher, int duration) {
        Assertions.assertTrue(watcher.shouldSample("", 10000, duration));
        Assertions.assertFalse(watcher.shouldSample("", 10000, duration - 1));
    }

    private SamplingPolicy getSamplingPolicy(String service, TraceSamplingPolicyWatcher watcher) {
        AtomicReference<SamplingPolicySettings> samplingPolicySettings = Whitebox.getInternalState(
            watcher, "samplingPolicySettings");
        return samplingPolicySettings.get().get(service);
    }
}
