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

import java.util.LinkedHashMap;

/**
 * A group of {@link Attribute}s.
 */
public class Attributes extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = -5013206854603694933L;

    /**
     * Instantiates a new Attributes.
     *
     * @param attributes the attributes
     */
    public Attributes(final Attribute... attributes) {
        for (final Attribute attribute : attributes) {
            this.put(attribute.getKey(), attribute.getValue());
        }
    }
}
