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

package org.apache.skywalking.oap.server.core.query.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class Process {
    @Setter
    private String id;
    @Setter
    private String name;
    @Setter
    private String serviceId;
    @Setter
    private String serviceName;
    @Setter
    private String instanceId;
    @Setter
    private String instanceName;
    @Setter
    private String agentId;
    @Setter
    private String detectType;
    @Setter
    private String profilingSupportStatus;
    private final List<Attribute> attributes;
    private final List<String> labels;

    public Process() {
        this.attributes = new ArrayList<>();
        this.labels = new ArrayList<>();
    }
}
