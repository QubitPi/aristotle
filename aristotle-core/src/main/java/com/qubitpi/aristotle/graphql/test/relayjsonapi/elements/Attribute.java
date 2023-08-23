/*
 * Copyright Jiaqi Liu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qubitpi.aristotle.graphql.test.relayjsonapi.elements;

import net.jcip.annotations.NotThreadSafe;

import java.util.Objects;

/**
 * {@link Attribute} represents a field in JSON response.
 */
@NotThreadSafe
public class Attribute {

    /**
     * The Key.
     */
    private final String key;

    /**
     * The Value.
     */
    private final Object value;

    /**
     * Constructor.
     *
     * @param key  Attribute name
     * @param value  Attribute value
     *
     * @throws NullPointerException if any argument is {@code null}
     */
    public Attribute(final String key, final Object value) {
        this.key = Objects.requireNonNull(key, "key");
        this.value = Objects.requireNonNull(value, "value");
    }

    /**
     * Returns name of this attribute.
     *
     * @return attribute name
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the value of this attribute.
     *
     * @return attribute value
     */
    public Object getValue() {
        return value;
    }
}
