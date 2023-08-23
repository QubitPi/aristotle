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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * {@link Document} represents the same concept as {@link graphql.language.Document GraphQL Document} but
 * specializes in serialization, in contrast to {@link graphql.language.Document GraphQL Document}, which is
 * designed for deserialization.
 * <p>
 * According to GraphQL grammar
 * <pre>
 * {@code
 * document : definition+;
 * }
 * </pre>
 * A {@link Document} is one or more {@link Definition}s.
 */
@Immutable
@ThreadSafe
public class Document {

    /**
     * Models the list of one or more {@link Definition}s.
     */
    private final List<Definition> definitions;

    /**
     * Private constructor.
     *
     * @param definitions  Query specs
     *
     * @throws NullPointerException if {@code definitions} is {@code null}
     */
    private Document(final List<Definition> definitions) {
        this.definitions = Objects.requireNonNull(definitions, "definitions");
    }

    /**
     * Creates a new GraphQL query object with the specified {@link Definition}s.
     *
     * @param definitions  The provided {@link Definition}s
     *
     * @return a new {@link Document} instance
     */
    public static Document of(final Definition... definitions) {
        return new Document(Arrays.asList(definitions));
    }

    /**
     * Returns the complete GraphQL query that this {@link Document} represents.
     *
     * @return a string representation of a GraphQL query
     */
    public String toQuery() {
        return getDefinitions().stream()
                .map(Definition::toGraphQlSpec)
                .collect(Collectors.joining(" "));
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final Document that = (Document) other;
        return this.getDefinitions().equals(that.getDefinitions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDefinitions());
    }

    @Override
    public String toString() {
        return toQuery();
    }

    /**
     * Returns all query specs.
     *
     * @return actual query object
     */
    private List<Definition> getDefinitions() {
        return definitions;
    }
}
