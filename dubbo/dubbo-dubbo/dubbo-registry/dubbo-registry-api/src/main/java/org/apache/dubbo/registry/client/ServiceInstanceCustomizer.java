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
package org.apache.dubbo.registry.client;

import org.apache.dubbo.common.extension.SPI;
import org.apache.dubbo.common.lang.Prioritized;
import org.apache.dubbo.registry.client.event.ServiceInstancePreRegisteredEvent;
import org.apache.dubbo.registry.client.event.listener.CustomizableServiceInstanceListener;

/**
 * The interface to customize {@link ServiceInstance the service instance} on {@link ServiceInstancePreRegisteredEvent}
 *
 * @see CustomizableServiceInstanceListener
 * @see ServiceInstancePreRegisteredEvent
 * @see ServiceInstance#getMetadata()
 * @since 2.7.5
 */
@SPI
public interface ServiceInstanceCustomizer extends Prioritized {

    /**
     * Customizes {@link ServiceInstance the service instance}
     *
     * @param serviceInstance {@link ServiceInstance the service instance}
     */
    void customize(ServiceInstance serviceInstance);
}
