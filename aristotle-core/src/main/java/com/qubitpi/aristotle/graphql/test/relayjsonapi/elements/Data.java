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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.jcip.annotations.NotThreadSafe;

import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * {@link Data} is for INTERNAL USE ONLY.
 * <p>
 * {@link Data} is a top level GraphQL response object.
 * <p>
 * A serialized {@link Data} has the following JSON form:
 * <pre>
 * {
 *     "data":{
 *         ......
 *     }
 * }
 * </pre>
 */
@NotThreadSafe // LinkedHashMap
public class Data extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = 8568263004760374305L;

    /**
     * A JSON serializer from regular Java objects(such as Map or List) to their JSON equivalents.
     */
    private static final Gson OBJECT_SERIALIZER = new GsonBuilder().serializeNulls().create();

    /**
     * Constructor.
     *
     * @param resource  The object to be serialized and enclosed inside {@code "data": {}} JSON
     */
    public Data(final Resource resource) {
        final Attribute attribute = (Attribute) resource;
        this.put("data", Collections.singletonMap(attribute.getKey(), attribute.getValue()));
    }

    /**
     * Serializes this response object to JSON.
     *
     * @return a Relay response
     */
    public String toJson() {
        return OBJECT_SERIALIZER.toJson(this);
    }
}
