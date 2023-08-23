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

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.util.Collections;

/**
 * {@link Resource} is for INTERNAL USE ONLY.
 * <p>
 * {@link Resource} represents all instances of an resource in a GraphQL JSON response:
 * <pre>
 * {@code
 * "book":{
 *     "edges":[
 *         {
 *             "node":{
 *                 "id":"1",
 *                 "title":"My first book",
 *                 "authors":{
 *                     "edges":[
 *                         {
 *                             "node":{
 *                                 "name":"Ricky Carmichael"
 *                             }
 *                         }
 *                     ]
 *                 }
 *             }
 *         }
 *     ]
 * }
 * }
 * </pre>
 */
@Immutable
@ThreadSafe
public class Resource extends Attribute {

    /**
     * Constructor.
     *
     * @param resourceName  The resource name
     * @param edges  All instances of the resource wrapped in {@link Edges}-{@link Node nodes}.
     */
    public Resource(final String resourceName, final Edges edges) {
        super(resourceName, Collections.singletonMap("edges", edges));
    }
}
