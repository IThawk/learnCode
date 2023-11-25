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

package org.apache.skywalking.oap.server.core.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StorageIDTest {
    @Test
    public void testRawBuild() {
        StorageID id = new StorageID();
        id.append("time_bucket", 202212141438L) //2022-12-14 14:38
          .append("entity_id", "encoded-service-name");

        Assertions.assertEquals("202212141438_encoded-service-name", id.build());
    }

    @Test
    public void testEqual() {
        StorageID id = new StorageID();
        id.append("time_bucket", 202212141438L) //2022-12-14 14:38
          .append("entity_id", "encoded-service-name");

        StorageID id2 = new StorageID();
        id2.append("time_bucket", 202212141438L) //2022-12-14 14:38
           .append("entity_id", "encoded-service-name");

        Assertions.assertEquals(true, id.equals(id2));
    }
}
