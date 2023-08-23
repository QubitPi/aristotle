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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * {@link Arguments} is a serializable object that models the concept of GraphQL arguments.
 * <p>
 * According to GraphQL grammar
 * <pre>
 * {@code
 * arguments : '(' argument+ ')';
 * }
 * </pre>
 * Arguments is a space-separated list of {@link Argument}s surrounded by a pair of parenthesis.
 *
 * @see Argument
 */
public class Arguments {

    /**
     * Models {@code argument+}.
     */
    private final List<Argument> arguments;

    /**
     * Private constructor.
     *
     * @param arguments  The provided {@link Argument}s
     */
    private Arguments(final List<Argument> arguments) {
        this.arguments = Objects.requireNonNull(arguments, "arguments");
    }


    /**
     * Creates an empty list of {@link Arguments arguments}.
     *
     * @return no argument
     */
    public static Arguments empty() {
        return new Arguments(Collections.emptyList());
    }

    /**
     * Creates {@link Arguments} with the specified {@link Argument}s.
     *
     * @param arguments  The provided {@link Argument}s
     *
     * @return object representing a list of {@link Argument}s
     */
    public static Arguments of(final Argument... arguments) {
        return new Arguments(Arrays.asList(arguments));
    }

    /**
     * Returns a GraphQL query string that representing an set of arguments.
     *
     * @return a sub-string of a GraphQL query
     */
    public String toGraphQlSpec() {
        return isEmpty()
                ? ""
                : String.format(
                        "(%s)",
                        getArguments().stream()
                                .map(Argument::toGraphQlSpec)
                                .collect(Collectors.joining(" "))
                );
    }

    /**
     * Returns whether or not this {@link Arguments} is empty, i.e. no argument is to be serialized.
     *
     * @return {@code true} if serializing this {@link Arguments} is skipped
     */
    public boolean isEmpty() {
        return getArguments().isEmpty();
    }

    /**
     * Returns all {@link Argument}s wrapped inside.
     *
     * @return actual {@link Argument}s
     */
    private List<Argument> getArguments() {
        return arguments;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final Arguments that = (Arguments) other;
        return Objects.equals(this.getArguments(), that.getArguments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArguments());
    }

    @Override
    public String toString() {
        return toGraphQlSpec();
    }
}
