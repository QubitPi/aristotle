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
package com.qubitpi.aristotle.graphql.test.relayjsonapi;

import org.bitbucket.jack20191124.graphql.test.relayjsonapi.elements.Attribute;
import org.bitbucket.jack20191124.graphql.test.relayjsonapi.elements.Attributes;
import org.bitbucket.jack20191124.graphql.test.relayjsonapi.elements.Data;
import org.bitbucket.jack20191124.graphql.test.relayjsonapi.elements.Edges;
import org.bitbucket.jack20191124.graphql.test.relayjsonapi.elements.Node;
import org.bitbucket.jack20191124.graphql.test.relayjsonapi.elements.Resource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.Collections;

/**
 * A DSL for programmatically constructing GraphQL responses in Relay's connection pattern format.
 * <p>
 * GraphQL response in Relay's format is essentially JSON. Escaping double quotes and typing pairs of open-close JSON
 * objects while maintaining readable are much more difficult than it should be as as the following:
 * <pre>
 * {
 *     "data":{
 *         "book":{
 *             "edges":[
 *                 {
 *                     "node":{
 *                         "id":"1",
 *                         "title":"My first book",
 *                         "authors":{
 *                             "edges":[
 *                                 {
 *                                     "node":{
 *                                         "name":"Ricky Carmichael"
 *                                     }
 *                                 }
 *                             ]
 *                         }
 *                     }
 *                 }
 *             ]
 *         }
 *     }
 * }
 * </pre>
 * {@link RelayJsonApiDsl} solves the problem by typesizing different JSON node so the JSON above can be easily and
 * readably constructed as follows:
 * <pre>
 * {@code
 * String response = datum(
 *                 resource(
 *                         "book",
 *                         edges(
 *                                 node(
 *                                         attribute("id", "1"),
 *                                         attribute("title", "My first book"),
 *                                         resource(
 *                                                 "authors",
 *                                                 edges(
 *                                                         node(
 *                                                                 attribute("name", "Ricky Carmichael")
 *
 *                                                         )
 *                                                 )
 *                                         )
 *                                 )
 *                         )
 *                 )
 *         ).toJson()
 * }
 * </pre>
 *
 * @see <a href="https://graphql.org/learn/pagination/#pagination-and-edges">Relay's connection pattern</a>
 */
@Immutable
@ThreadSafe
public final class RelayJsonApiDsl {

    /**
     * Serializes expected JSON object to a string.
     */
    private static final Gson JSON_SERIALIZER = new GsonBuilder()
            .serializeNulls().create();

    /**
     * Constructor.
     * <p>
     * Suppress default constructor for noninstantiability.
     */
    private RelayJsonApiDsl() {
        throw new AssertionError();
    }

    /**
     * Creates a complete GraphQL response.
     *
     * @param resource  The object representing the response JSON data of an resource
     *
     * @return a serializable GraphQL response object
     */
    public static Data datum(final Resource resource) {
        return new Data(resource);
    }

    /**
     * Creates an object representing the response JSON data of an resource.
     *
     * @param resourceName  The resource name
     * @param edges  All data returned about the resource
     *
     * @return a serializable response resource
     */
    public static Resource resource(final String resourceName, final Edges edges) {
        return new Resource(resourceName, edges);
    }

    /**
     * Converts a Java Object into its JSON representation.
     * <p>
     * This method is very useful when {@link #attribute(String, Object)} has an object value.
     *
     * @param object  The object to be converted
     *
     * @return a JSON string
     */
    public static String toJson(final Object object) {
        return JSON_SERIALIZER.toJson(object);
    }

    /**
     * Creates a key-value pair representing the hydrated field of an returned resource.
     *
     * @param name  The key
     * @param value  The value
     *
     * @return an resource field with value
     */
    public static Attribute attribute(final String name, final Object value) {
        return new Attribute(name, value);
    }

    /**
     * Creates a single {@link Attribute} group.
     *
     * @param attribute  The single attribute
     *
     * @return the single {@link Attribute} group
     */
    public static Attributes attribute(final Attribute attribute) {
        return new Attributes(attribute);
    }

    /**
     * Creates a {@link Node} instance.
     *
     * @param attributes  All fields in this {@link Node} instance
     *
     * @return a {@link Node} instance
     */
    public static Node node(final Attribute... attributes) {
        return new Node(new Attributes(attributes));
    }

    /**
     * Creates {@link Edges} instance.
     *
     * @param nodes  All {@link Node}s wrapped inside this {@link Edges} instance
     *
     * @return an {@link Edges} instance
     */
    public static Edges edges(final Node... nodes) {
        return new Edges(Arrays.asList(nodes));
    }

    /**
     * Creates an empty {@link Edges} instance.
     *
     * @return a {@link Edges} instance with no returned data about an resource
     */
    public static Edges edges() {
        return new Edges(Collections.emptyList());
    }
}
