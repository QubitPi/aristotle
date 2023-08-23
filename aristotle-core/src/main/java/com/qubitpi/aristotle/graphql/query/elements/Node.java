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
 * {@link Node} represents the same concepts as the "node" in Relay's connection pattern.
 * <pre>
 * {
 *     hero {
 *         name
 *         friends(first:2) {
 *             edges {
 *                 node {    ---------------
 *                     name                +--- A node
 *                 }         ---------------
 *                 cursor
 *             }
 *         }
 *     }
 * }
 * </pre>
 *
 * @see <a href="https://graphql.org/learn/pagination/#pagination-and-edges">Relay's connectin pattern</a>
 */
@Immutable
@ThreadSafe
public class Node implements Selection {

    /**
     * Serialization format.
     */
    private static final String FORMAT = "node %s";

    /**
     * The selection wrapped inside this {@link Node}.
     * <p>
     * For example, the "name" in the example.
     */
    private final SelectionSet fields;

    /**
     * Private constructor.
     *
     * @param fields  The {@link Selection}s wrapped in this {@link Node}
     *
     * @throws NullPointerException if the {@code fields} is {@code null}
     */
    private Node(final SelectionSet fields) {
        this.fields = Objects.requireNonNull(fields, "fields");
    }

    /**
     * Creates a new instance of {@link Node}.
     *
     * @param fields  The {@link Selection}s wrapped in this {@link Node}
     *
     * @return a {@link Selection}
     */
    public static Selection of(final SelectionSet fields) {
        return new Node(fields);
    }

    @Override
    public String toGraphQlSpec() {
        return String.format(
                FORMAT,
                getFields().toGraphQlSpec()
        );
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final Node that = (Node) other;
        return Objects.equals(this.getFields(), that.getFields());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFields());
    }

    @Override
    public String toString() {
        return toGraphQlSpec();
    }

    /**
     * Returns the wrapped {@link SelectionSet selections}.
     *
     * @return wrapped {@link Selection selections}
     */
    private SelectionSet getFields() {
        return fields;
    }
}
