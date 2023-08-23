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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * {@link Edges} is for INTERNAL USE ONLY.
 * <p>
 * {@link Edges} is a list of {@link LinkedList.Node}s. Its serialized form is
 * <pre>
 * "edges":[
 *     {
 *         "node": ...
 *     },
 *     {
 *         "node": ...
 *     },
 *     ...
 * ]
 * </pre>
 */
@NotThreadSafe // LinkedList
public class Edges extends LinkedList<Map<String, Object>> {

    private static final long serialVersionUID = 7587614051331106241L;

    /**
     * Constructor.
     *
     * @param nodes  The list of {@link LinkedList.Node}s
     */
    public Edges(final List<LinkedList.Node> nodes) {
        if (nodes != null && !nodes.isEmpty()) {
            nodes.forEach(node -> {
                final Map<String, Object> attributes = new LinkedHashMap<>();
                attributes.put("node", node);
                this.add(attributes);
            });
        }
    }
}
