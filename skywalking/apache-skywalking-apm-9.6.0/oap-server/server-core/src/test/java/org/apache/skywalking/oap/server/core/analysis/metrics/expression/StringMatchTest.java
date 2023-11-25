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

package org.apache.skywalking.oap.server.core.analysis.metrics.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringMatchTest {

    @Test
    public void integerShouldEqualWhenLargerThan128() {
        Integer a = 334;
        Integer b = 334;
        boolean match = new StringMatch().match(a, b);
        assertTrue(match);
    }

    @Test
    public void longShouldEqualWhenLargerThan128() {
        Long a = 334L;
        Long b = 334L;
        boolean match = new StringMatch().match(a, b);
        assertTrue(match);
    }

    @Test
    public void doubleShouldEqualWhenLargerThan128() {
        Double a = 334.0;
        Double b = 334.0;
        boolean match = new StringMatch().match(a, b);
        assertTrue(match);
    }

    @Test
    public void floatShouldEqualWhenLargerThan128() {
        Float a = 334.0F;
        Float b = 334.0F;
        boolean match = new StringMatch().match(a, b);
        assertTrue(match);
    }

    @Test
    public void stringShouldEqual() {
        assertTrue(new StringMatch().match("\"a\"", "a"));
        assertTrue(new StringMatch().match("a", "a"));
        assertFalse(new StringMatch().match("\"a\"", "ab"));
    }
}
