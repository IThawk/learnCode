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

package org.apache.skywalking.oap.server.core.config.group;

import org.apache.skywalking.oap.server.core.config.group.uri.quickmatch.QuickUriGroupingRule;
import org.apache.skywalking.oap.server.library.util.StringFormatGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EndpointGroupingRuleReaderTest {
    @Test
    public void testReadingRule() {
        EndpointGroupingRuleReader reader = new EndpointGroupingRuleReader(this.getClass()
                                                                               .getClassLoader()
                                                                               .getResourceAsStream(
                                                                                   "endpoint-name-grouping.yml"));
        final QuickUriGroupingRule rule = reader.read();

        StringFormatGroup.FormatResult formatResult = rule.format("serviceA", "/prod/123");
        Assertions.assertTrue(formatResult.isMatch());
        Assertions.assertEquals("/prod/{var}", formatResult.getReplacedName());

        // This will always match, since after slicing length is 1, which goes into special handling
        formatResult = rule.format("serviceA", "/prod/");
        Assertions.assertTrue(formatResult.isMatch());
        Assertions.assertEquals("/prod/", formatResult.getReplacedName());

        formatResult = rule.format("serviceA", "/prod/123/456");
        Assertions.assertFalse(formatResult.isMatch());
        Assertions.assertEquals("/prod/123/456", formatResult.getReplacedName());

        formatResult = rule.format("serviceB", "/prod/123");
        Assertions.assertFalse(formatResult.isMatch());
    }
}
