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

package org.apache.skywalking.oap.server.storage.plugin.jdbc.mysql;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This is a very special test case. It isn't for feature testing.
 * <p>
 * In Apache, we can't redistribute MySQL Driver, because of GPL license, but we deliver MySQL solution sourceScopeId
 * codes and distribution by using JDBC.
 */
public class PreventRedistributionMySQLDriverTest {
    @Test
    public void TestMySQLDriverNotExist() throws ClassNotFoundException {
        assertThrows(ClassNotFoundException.class, () -> Class.forName("com.mysql.cj.jdbc.Driver"));
    }
}
