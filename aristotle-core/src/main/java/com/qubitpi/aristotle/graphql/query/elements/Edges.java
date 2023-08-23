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
package com.qubitpi.aristotle.graphql.query.elements;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.util.Objects;

/**
 * {@link Edges} represents the same concepts as the "edges" in Relay's connection pattern.
 * <pre>
 * {
 *     hero {
 *         name
 *         friends(first:2) {
 *             edges {       ---------------
 *                 node {                  |
 *                     name                +--- edges
 *                 }                       |
 *                 cursor                  |
 *             }            ---------------
 *         }
 *     }
 * }
 * </pre>
 *
 * @see <a href="https://graphql.org/learn/pagination/#pagination-and-edges">Relay's connectin pattern</a>
 */
@Immutable
@ThreadSafe
public class Edges implements Selection {

    /**
     * Models the list of {@link Node}s.
     */
    private final Node node;

    /**
     * Private constructor.
     *
     * @param node  Wrapped inside edge
     */
    private Edges(final Node node) {
        this.node = Objects.requireNonNull(node, "node");
    }

    /**
     * Creates a new edge pointing to a specified {@link Node}.
     *
     * @param node  The provided {@link Node}
     *
     * @return a new {@link Edges} instance
     */
    public static Selection to(final Node node) {
        return new Edges(node);
    }

    /**
     * Returns the query string that corresponds to the edges that connects sub-graphs in a GraphQL query.
     *
     * @return a sub-string of a GraphQL query
     */
    @Override
    public String toGraphQlSpec() {
        return String.format("edges {%s}", getNode().toGraphQlSpec());
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final Edges that = (Edges) other;
        return Objects.equals(this.getNode(), that.getNode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNode());
    }

    @Override
    public String toString() {
        return toGraphQlSpec();
    }

    /**
     * Returns the {@link Node} wrapped/pointed by this edge.
     *
     * @return connection
     */
    private Node getNode() {
        return node;
    }
}
