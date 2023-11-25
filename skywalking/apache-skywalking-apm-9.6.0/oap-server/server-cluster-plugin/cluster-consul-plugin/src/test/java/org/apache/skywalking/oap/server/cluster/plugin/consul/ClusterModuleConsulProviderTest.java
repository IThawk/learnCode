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

package org.apache.skywalking.oap.server.cluster.plugin.consul;

import com.google.common.collect.Lists;
import com.google.common.net.HostAndPort;
import com.orbitz.consul.Consul;
import org.apache.skywalking.oap.server.core.CoreModule;
import org.apache.skywalking.oap.server.core.cluster.ClusterModule;
import org.apache.skywalking.oap.server.library.module.ModuleManager;
import org.apache.skywalking.oap.server.library.module.ModuleStartException;
import org.apache.skywalking.oap.server.telemetry.TelemetryModule;
import org.apache.skywalking.oap.server.telemetry.none.NoneTelemetryProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyCollection;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClusterModuleConsulProviderTest {

    private ClusterModuleConsulProvider provider = new ClusterModuleConsulProvider();

    @Mock
    private ModuleManager moduleManager;
    @Mock
    private NoneTelemetryProvider telemetryProvider;

    @BeforeEach
    public void before() {
        TelemetryModule telemetryModule = Mockito.spy(TelemetryModule.class);
        Whitebox.setInternalState(telemetryModule, "loadedProvider", telemetryProvider);
        provider.setManager(moduleManager);
        Whitebox.setInternalState(provider, "config", new ClusterModuleConsulConfig());
    }

    @Test
    public void name() {
        assertEquals("consul", provider.name());
    }

    @Test
    public void module() {
        assertEquals(ClusterModule.class, provider.module());
    }

    @Test
    public void prepareWithNonHost() throws Exception {
        assertThrows(ModuleStartException.class, () -> provider.prepare());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void prepare() throws Exception {
        ClusterModuleConsulConfig consulConfig = new ClusterModuleConsulConfig();
        consulConfig.setHostPort("10.0.0.1:1000,10.0.0.2:1001");
        Whitebox.setInternalState(provider, "config", consulConfig);

        Consul consulClient = mock(Consul.class);
        Consul.Builder builder = mock(Consul.Builder.class);
        when(builder.build()).thenReturn(consulClient);

        try (MockedStatic<Consul> ignored = mockStatic(Consul.class)) {
            when(Consul.builder()).thenReturn(builder);
            when(builder.withConnectTimeoutMillis(anyLong())).thenReturn(builder);

            when(builder.withMultipleHostAndPort(anyCollection(), anyLong())).thenReturn(builder);
            provider.prepare();

            ArgumentCaptor<Collection> addressCaptor = ArgumentCaptor.forClass(Collection.class);
            ArgumentCaptor<Long> timeCaptor = ArgumentCaptor.forClass(long.class);
            verify(builder).withMultipleHostAndPort(addressCaptor.capture(), timeCaptor.capture());

            List<HostAndPort> address = (List<HostAndPort>) addressCaptor.getValue();
            assertEquals(2, address.size());
            assertEquals(
                    Lists.newArrayList(HostAndPort.fromParts("10.0.0.1", 1000), HostAndPort.fromParts("10.0.0.2", 1001)),
                    address
            );
        }
    }

    @Test
    public void prepareSingle() throws Exception {
        ClusterModuleConsulConfig consulConfig = new ClusterModuleConsulConfig();
        consulConfig.setHostPort("10.0.0.1:1000");
        Whitebox.setInternalState(provider, "config", consulConfig);

        Consul consulClient = mock(Consul.class);
        Consul.Builder builder = mock(Consul.Builder.class);
        when(builder.build()).thenReturn(consulClient);

        try (MockedStatic<Consul> ignored = mockStatic(Consul.class)) {
            when(Consul.builder()).thenReturn(builder);
            when(builder.withConnectTimeoutMillis(anyLong())).thenReturn(builder);

            when(builder.withHostAndPort(any())).thenReturn(builder);

            provider.prepare();

            ArgumentCaptor<HostAndPort> hostAndPortArgumentCaptor = ArgumentCaptor.forClass(HostAndPort.class);
            verify(builder).withHostAndPort(hostAndPortArgumentCaptor.capture());

            HostAndPort address = hostAndPortArgumentCaptor.getValue();
            assertEquals(HostAndPort.fromParts("10.0.0.1", 1000), address);
        }
    }

    @Test
    public void start() {
        provider.start();
    }

    @Test
    public void notifyAfterCompleted() {
        provider.notifyAfterCompleted();
    }

    @Test
    public void requiredModules() {
        String[] modules = provider.requiredModules();
        assertArrayEquals(new String[] {CoreModule.NAME}, modules);
    }
}
