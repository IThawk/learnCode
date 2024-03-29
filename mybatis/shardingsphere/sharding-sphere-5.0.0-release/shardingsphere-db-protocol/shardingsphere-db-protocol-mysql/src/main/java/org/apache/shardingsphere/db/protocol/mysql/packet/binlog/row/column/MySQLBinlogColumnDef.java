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

package org.apache.shardingsphere.db.protocol.mysql.packet.binlog.row.column;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.shardingsphere.db.protocol.mysql.constant.MySQLBinaryColumnType;

/**
 * Column definition.
 *
 * @see <a href="https://dev.mysql.com/doc/internals/en/table-map-event.html">TABLE_MAP_EVENT</a>
 */
@RequiredArgsConstructor
@Getter
@Setter
public final class MySQLBinlogColumnDef {
    
    private final MySQLBinaryColumnType columnType;
    
    private int columnMeta;
}
