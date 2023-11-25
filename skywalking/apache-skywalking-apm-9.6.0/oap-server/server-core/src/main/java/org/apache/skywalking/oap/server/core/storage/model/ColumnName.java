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

package org.apache.skywalking.oap.server.core.storage.model;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.oap.server.core.storage.annotation.Column;

/**
 * Short column name unsupported for now. No define in @Column annotation. The storage implementation need to use name
 * to do match.
 */
@Slf4j
@Getter
@ToString
public class ColumnName {
    private final String name;
    private String storageName;

    public ColumnName(Column column) {
        storageName = name = column.name();
    }

    public void overrideName(String oldName, String storageName) {
        if (name.equals(oldName)) {
            this.storageName = storageName;
        }
    }
}
