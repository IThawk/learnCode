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

package org.apache.skywalking.oap.server.receiver.ebpf.provider;

import org.apache.skywalking.oap.server.core.CoreModule;
import org.apache.skywalking.oap.server.core.server.GRPCHandlerRegister;
import org.apache.skywalking.oap.server.library.module.ModuleDefine;
import org.apache.skywalking.oap.server.library.module.ModuleProvider;
import org.apache.skywalking.oap.server.library.module.ModuleStartException;
import org.apache.skywalking.oap.server.library.module.ServiceNotProvidedException;
import org.apache.skywalking.oap.server.receiver.ebpf.module.EBPFReceiverModule;
import org.apache.skywalking.oap.server.receiver.ebpf.provider.handler.ContinuousProfilingServiceHandler;
import org.apache.skywalking.oap.server.receiver.ebpf.provider.handler.EBPFProcessServiceHandler;
import org.apache.skywalking.oap.server.receiver.ebpf.provider.handler.EBPFProfilingServiceHandler;
import org.apache.skywalking.oap.server.receiver.sharing.server.SharingServerModule;

public class EBPFReceiverProvider extends ModuleProvider {

    private EBPFReceiverModuleConfig config;

    @Override
    public String name() {
        return "default";
    }

    @Override
    public Class<? extends ModuleDefine> module() {
        return EBPFReceiverModule.class;
    }

    @Override
    public ConfigCreator<EBPFReceiverModuleConfig> newConfigCreator() {
        return new ConfigCreator<EBPFReceiverModuleConfig>() {
            @Override
            public Class<EBPFReceiverModuleConfig> type() {
                return EBPFReceiverModuleConfig.class;
            }

            @Override
            public void onInitialized(EBPFReceiverModuleConfig initialized) {
                config = initialized;
            }
        };
    }

    @Override
    public void prepare() throws ServiceNotProvidedException, ModuleStartException {
    }

    @Override
    public void start() throws ServiceNotProvidedException, ModuleStartException {
        final GRPCHandlerRegister grpcHandlerRegister = getManager().find(SharingServerModule.NAME)
                .provider()
                .getService(GRPCHandlerRegister.class);
        grpcHandlerRegister.addHandler(new EBPFProcessServiceHandler(getManager()));
        grpcHandlerRegister.addHandler(new EBPFProfilingServiceHandler(getManager()));
        grpcHandlerRegister.addHandler(new ContinuousProfilingServiceHandler(getManager(), this.config));
    }

    @Override
    public void notifyAfterCompleted() throws ServiceNotProvidedException, ModuleStartException {

    }

    @Override
    public String[] requiredModules() {
        return new String[] {
                CoreModule.NAME,
                SharingServerModule.NAME
        };
    }
}
