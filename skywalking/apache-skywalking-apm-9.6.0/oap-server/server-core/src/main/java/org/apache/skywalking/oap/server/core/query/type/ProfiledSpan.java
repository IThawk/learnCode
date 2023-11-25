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

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfiledSpan {

    private int spanId;
    private int parentSpanId;
    private String segmentId;
    private List<Ref> refs;
    private String serviceCode;
    private String serviceInstanceName;
    private long startTime;
    private long endTime;
    private String endpointName;
    private String type;
    private String peer;
    private String component;
    private boolean isError;
    private String layer;
    private final List<KeyValue> tags;
    private final List<LogEntity> logs;
    private boolean profiled;

    public ProfiledSpan() {
        this.tags = new ArrayList<>();
        this.logs = new ArrayList<>();
    }
}
