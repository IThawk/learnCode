/*
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.apache.skywalking.oap.server.core.source;

import lombok.Getter;
import lombok.Setter;
import org.apache.skywalking.oap.server.core.analysis.IDManager;

import static org.apache.skywalking.oap.server.core.source.DefaultScopeDefine.ENDPOINT_CATALOG_NAME;
import static org.apache.skywalking.oap.server.core.source.DefaultScopeDefine.MESSAGE_QUEUE_ENDPOINT_ACCESS;

@ScopeDeclaration(id = MESSAGE_QUEUE_ENDPOINT_ACCESS, name = "MQEndpointAccess", catalog = ENDPOINT_CATALOG_NAME)
@ScopeDefaultColumn.VirtualColumnDefinition(fieldName = "entityId", columnName = "entity_id", isID = true, type = String.class)
public class MQEndpointAccess extends Source {
    @Override
    public int scope() {
        return MESSAGE_QUEUE_ENDPOINT_ACCESS;
    }

    @Override
    public String getEntityId() {
        if (entityId == null) {
            entityId = IDManager.EndpointID.buildId(serviceId, endpoint);
        }
        return entityId;
    }

    private String entityId;

    @Getter
    @ScopeDefaultColumn.DefinedByField(columnName = "service_id")
    private String serviceId;

    @Getter
    @Setter
    @ScopeDefaultColumn.DefinedByField(columnName = "service_name", requireDynamicActive = true)
    private String serviceName;

    @Getter
    @Setter
    @ScopeDefaultColumn.DefinedByField(columnName = "endpoint", requireDynamicActive = true)
    private String endpoint;

    @Getter
    @Setter
    private int typeId;
    @Getter
    @Setter
    private long transmissionLatency;
    @Getter
    @Setter
    private boolean status;

    @Getter
    @Setter
    private MQOperation operation;

    @Override
    public void prepare() {
        this.serviceId = IDManager.ServiceID.buildId(serviceName, false);
    }
}
