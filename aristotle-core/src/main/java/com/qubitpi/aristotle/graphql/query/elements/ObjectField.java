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

import java.util.Objects;

/**
 * {@link ObjectField} represents the same concept as GraphQL {@link graphql.language.ObjectField}, but specializes
 * in serialization, in contrast to GraphQL{@link graphql.language.ObjectField}, which is designed for deserialization.
 * <p>
 * According to GraphQL grammar (6.0):
 * <pre>
 * {@code
 * field : alias? name arguments? directives? selectionSet?
 * }
 * </pre>
 * A {@link ObjectField} is a name with optional alias, arguments, directives, or selection set. In this implementation,
 * arguments and directives are supported.
 * <p>
 * TODO - support alias and directives
 */
public final class ObjectField implements Field {

    /**
     * The "name" TOKEN defined in GraphQL grammar.
     */
    private final String name;

    /**
     * Models "arguments".
     */
    private final Arguments arguments;

    /**
     * Models a "selections set".
     */
    private final SelectionSet selectionSet;

    /**
     * Private constructor.
     *
     * @param name  The field name
     * @param arguments  The arguments for sub-selection
     * @param selectionSet  A sub-selection
     *
     * @throws NullPointerException if any argument is {@code null}
     */
    private ObjectField(final String name, final Arguments arguments, final SelectionSet selectionSet) {
        this.name = Objects.requireNonNull(name, "name");
        this.arguments = Objects.requireNonNull(arguments, "arguments");
        this.selectionSet = Objects.requireNonNull(selectionSet, "selectionSet");
    }

    /**
     * Constructs an {@link ObjectField} without {@link Arguments}.
     *
     * @param name  The field name
     * @param selectionSet  A sub-selection
     *
     * @return a new {@link ObjectField} instance
     */
    public static Field withoutArguments(final String name, final SelectionSet selectionSet) {
        return new ObjectField(name, Arguments.empty(), selectionSet);
    }

    /**
     * Constructs an {@link ObjectField}.
     *
     * @param name  The field name
     * @param arguments  The arguments for sub-selection
     * @param selectionSet  A sub-selection
     *
     * @return a new {@link ObjectField} instance
     */
    public static Field of(final String name, final Arguments arguments, final SelectionSet selectionSet) {
        return new ObjectField(name, arguments, selectionSet);
    }

    @Override
    public String toGraphQlSpec() {
        return String.format(
                "%s%s %s",
                getName(),
                getArguments().toGraphQlSpec(),
                getSelectionSet().toGraphQlSpec()
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
        final ObjectField that = (ObjectField) other;
        return Objects.equals(this.getName(), that.getName()) &&
                Objects.equals(this.getArguments(), that.getArguments()) &&
                Objects.equals(this.getSelectionSet(), that.getSelectionSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getArguments(), getSelectionSet());
    }

    @Override
    public String toString() {
        return toGraphQlSpec();
    }

    /**
     * Returns field name.
     *
     * @return field name
     */
    private String getName() {
        return name;
    }

    /**
     * Returns field arguments.
     *
     * @return field arguments
     */
    private Arguments getArguments() {
        return arguments;
    }

    /**
     * Returns sub-selections of this {@link Field}.
     *
     * @return sub-selections
     */
    private SelectionSet getSelectionSet() {
        return selectionSet;
    }
}
