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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * {@link VariableDefinitions} is a serializable object that models the concept of GraphQL {@code variableDefinitions}
 * in its grammar.
 * <p>
 * According to GraphQL grammar
 * <pre>
 * {@code
 * variableDefinitions : '(' variableDefinition+ ')';
 * }
 * </pre>
 * {@link VariableDefinitions} is a space-separated list of {@link VariableDefinition}s surrounded by a pair of
 * parenthesis.
 *
 * @see VariableDefinitions
 */
@Immutable
@ThreadSafe
public class VariableDefinitions {

    /**
     * Models the space-separated list of {@link VariableDefinition}s.
     */
    private final List<VariableDefinition> variableDefinitions;

    /**
     * Private constructor.
     *
     * @param variableDefinitions  A list of wrapped {@link VariableDefinition}s
     *
     * @throws NullPointerException if {@code variableDefinitions} is {@code null}
     */
    private VariableDefinitions(final List<VariableDefinition> variableDefinitions) {
        this.variableDefinitions = Collections.unmodifiableList(
                new ArrayList<>(Objects.requireNonNull(variableDefinitions, "variableDefinitions"))
        );
    }

    /**
     * Creates a new instance {@link VariableDefinitions} wrapping a specified array of {@link VariableDefinition}s.
     *
     * @param variableDefinitions  The provided {@link VariableDefinition}s
     *
     * @return a new object
     */
    public static VariableDefinitions of(final VariableDefinition... variableDefinitions) {
        return new VariableDefinitions(Arrays.asList(variableDefinitions));
    }

    /**
     * Returns a GraphQL query string that representing an set of variable definition.
     *
     * @return a sub-string of a GraphQL query
     */
    public String toGraphQlSpec() {
        return getVariableDefinitions().isEmpty()
                ? ""
                : String.format(
                        "(%s)",
                        getVariableDefinitions().stream()
                                .map(VariableDefinition::toGraphQlSpec)
                                .collect(Collectors.joining(" "))
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

        final VariableDefinitions that = (VariableDefinitions) other;
        return Objects.equals(this.getVariableDefinitions(), that.getVariableDefinitions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVariableDefinitions());
    }

    @Override
    public String toString() {
        return toGraphQlSpec();
    }

    /**
     * Returns the wrapped {@link VariableDefinition}s.
     *
     * @return actual variables
     */
    private List<VariableDefinition> getVariableDefinitions() {
        return variableDefinitions;
    }
}
