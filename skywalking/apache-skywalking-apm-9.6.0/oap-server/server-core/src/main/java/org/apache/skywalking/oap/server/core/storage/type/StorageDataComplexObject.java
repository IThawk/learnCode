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

package org.apache.skywalking.oap.server.core.storage.type;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;

/**
 * StorageDataComplexObject implementation supports String-Object interconversion.
 */
@JsonSerialize(using = StorageDataComplexObject.Serializer.class)
public interface StorageDataComplexObject<T> {
    /**
     * @return string representing this object.
     */
    String toStorageData();

    /**
     * Initialize this object based on the given string data.
     */
    void toObject(String data);

    /**
     * Initialize the object based on the given source.
     */
    void copyFrom(T source);

    final class Serializer extends JsonSerializer<StorageDataComplexObject<?>> {
        @Override
        public void serialize(
            final StorageDataComplexObject value,
            final JsonGenerator gen,
            final SerializerProvider provider)
            throws IOException {
            gen.writeString(value.toStorageData());
        }
    }
}
