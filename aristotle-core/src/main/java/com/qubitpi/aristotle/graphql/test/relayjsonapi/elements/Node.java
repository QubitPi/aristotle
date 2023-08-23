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

import java.util.LinkedHashMap;

/**
 * {@link Node} is for INTERNAL USE ONLY.
 * <p>
 * A {@link Node} represents an instance of
 * resource in GraphQL JSON response:
 * <pre>
 * {
 *     "field1": "value1",
 *     "field2" : "value2",
 *     "resource": {
 *         ...
 *     }
 * }
 * </pre>
 */
@NotThreadSafe // LinkedHashMap
public class Node extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = 7711282937931683426L;

    /**
     * Constructor.
     *
     * @param attributes  All field-value pairs representing a resource instance
     */
    public Node(final Attributes attributes) {
        this.putAll(attributes);
    }
}
