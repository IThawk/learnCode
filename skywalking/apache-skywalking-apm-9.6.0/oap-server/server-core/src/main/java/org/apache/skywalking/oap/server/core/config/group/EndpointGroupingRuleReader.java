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

import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.skywalking.oap.server.core.config.group.uri.quickmatch.QuickUriGroupingRule;
import org.apache.skywalking.oap.server.library.util.StringUtil;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

/**
 * Read the input stream including the default endpoint grouping rules. And trans
 */
public class EndpointGroupingRuleReader {
    private Map yamlData;

    public EndpointGroupingRuleReader(InputStream inputStream) {
        Yaml yaml = new Yaml(new SafeConstructor(new LoaderOptions()));
        yamlData = (Map) yaml.load(inputStream);
    }

    public EndpointGroupingRuleReader(Reader io) {
        Yaml yaml = new Yaml(new SafeConstructor(new LoaderOptions()));
        yamlData = (Map) yaml.load(io);
    }

    /**
     * @return the loaded rules.
     */
    QuickUriGroupingRule read() {
        QuickUriGroupingRule quickUriGroupingRule = new QuickUriGroupingRule();

        if (Objects.nonNull(yamlData)) {
            List rulesData = (List) yamlData.get("grouping");
            if (rulesData != null) {
                rulesData.forEach(ruleObj -> {
                    final Map rule = (Map) ruleObj;
                    final String serviceName = (String) rule.get("service-name");
                    if (StringUtil.isEmpty(serviceName)) {
                        throw new IllegalArgumentException("service-name can't be empty");
                    }
                    final List endpointRules = (List) rule.get("rules");
                    if (endpointRules != null) {
                        endpointRules.forEach(endpointRuleObj -> {
                            final String pattern = (String) endpointRuleObj;
                            if (StringUtil.isEmpty(pattern)) {
                                return;
                            }
                            quickUriGroupingRule.addRule(serviceName, pattern);
                        });
                    }
                });
            }
        }

        return quickUriGroupingRule;
    }
}
