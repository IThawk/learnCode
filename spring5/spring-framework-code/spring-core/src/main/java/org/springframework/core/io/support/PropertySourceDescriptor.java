/*
 * Copyright 2002-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.core.io.support;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.env.PropertySource;
import org.springframework.lang.Nullable;

/**
 * Describe a {@link PropertySource}.
 *
 * @param locations the locations to consider
 * @param ignoreResourceNotFound whether to fail if a location does not exist
 * @param name the name of the property source, or {@code null} to infer one
 * @param propertySourceFactory the {@link PropertySourceFactory} to use, or
 * {@code null} to use the default
 * @param encoding the encoding, or {@code null} to use the default encoding
 * @author Stephane Nicoll
 * @since 6.0
 */
public record PropertySourceDescriptor(List<String> locations, boolean ignoreResourceNotFound,
		@Nullable String name, @Nullable Class<? extends PropertySourceFactory> propertySourceFactory,
		@Nullable String encoding) {

	/**
	 * Create a descriptor with the specified locations.
	 * @param locations the locations to consider
	 */
	public PropertySourceDescriptor(String... locations) {
		this(Arrays.asList(locations), false, null, null, null);
	}

}
