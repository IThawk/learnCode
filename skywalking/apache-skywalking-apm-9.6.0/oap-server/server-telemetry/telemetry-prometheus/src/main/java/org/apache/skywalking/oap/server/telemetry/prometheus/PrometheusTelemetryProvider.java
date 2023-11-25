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

package org.apache.skywalking.oap.server.telemetry.prometheus;

import io.prometheus.client.hotspot.DefaultExports;
import org.apache.skywalking.oap.server.library.module.ModuleDefine;
import org.apache.skywalking.oap.server.library.module.ModuleProvider;
import org.apache.skywalking.oap.server.library.module.ModuleStartException;
import org.apache.skywalking.oap.server.library.module.ServiceNotProvidedException;
import org.apache.skywalking.oap.server.telemetry.TelemetryModule;
import org.apache.skywalking.oap.server.telemetry.api.MetricsCollector;
import org.apache.skywalking.oap.server.telemetry.api.MetricsCreator;
import org.apache.skywalking.oap.server.telemetry.prometheus.httpserver.HttpServer;

/**
 * Start the Prometheus
 */
public class PrometheusTelemetryProvider extends ModuleProvider {
    private PrometheusConfig config;

    @Override
    public String name() {
        return "prometheus";
    }

    @Override
    public Class<? extends ModuleDefine> module() {
        return TelemetryModule.class;
    }

    @Override
    public ConfigCreator newConfigCreator() {
        return new ConfigCreator<PrometheusConfig>() {
            @Override
            public Class type() {
                return PrometheusConfig.class;
            }

            @Override
            public void onInitialized(final PrometheusConfig initialized) {
                config = initialized;
            }
        };
    }

    @Override
    public void prepare() throws ServiceNotProvidedException, ModuleStartException {
        this.registerServiceImplementation(MetricsCreator.class, new PrometheusMetricsCreator());
        this.registerServiceImplementation(MetricsCollector.class, new PrometheusMetricsCollector());
        try {
            new HttpServer(config).start();
        } catch (InterruptedException e) {
            throw new ModuleStartException(e.getMessage(), e);
        }

        DefaultExports.initialize();
    }

    @Override
    public void start() throws ServiceNotProvidedException, ModuleStartException {

    }

    @Override
    public void notifyAfterCompleted() throws ServiceNotProvidedException, ModuleStartException {
    }

    @Override
    public String[] requiredModules() {
        return new String[0];
    }
}
