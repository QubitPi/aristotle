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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * {@link SelectionSet} is a serializable object that models the concept of a group of field selection in GraphQL; it
 * represents the same concept as {@link graphql.language.SelectionSet GraphQL SelectionSet} but specializes in
 * serialization, in contrast to {@link graphql.language.SelectionSet GraphQL SelectionSet} which is designed for
 * deserialization.
 * <p>
 * According to GraphQL grammar
 * <pre>
 * {@code
 * operationDefinition:
 *     selectionSet |
 *     operationType  name? variableDefinitions? directives? selectionSet;
 *
 * selectionSet :  '{' selection+ '}';
 * }
 * </pre>
 * SelectionSet is a sub-type of {@link OperationDefinition} and is a space-separated list of {@link Selection}s
 * surrounded by a pair of curly braces.
 *
 * @see Selection
 */
@Immutable
@ThreadSafe
public class SelectionSet implements OperationDefinition {

    /**
     * Models {@code selection+}.
     */
    private final List<Selection> selections;

    /**
     * Private constructor.
     *
     * @param selections  The specified list of {@link Selection}s
     */
    private SelectionSet(final List<Selection> selections) {
        this.selections = Collections.unmodifiableList(
                new LinkedList<>(Objects.requireNonNull(selections, "selections"))
        );
    }

    /**
     * Creates a new {@link SelectionSet}.
     *
     * @param selections  Set of {@link Selection}s
     *
     * @return a {@link SelectionSet} instance
     */
    public static SelectionSet of(final Selection... selections) {
        return new SelectionSet(Arrays.asList(selections));
    }

    @Override
    public String toGraphQlSpec() {
        return String.format(
                "{%s}",
                getSelections().stream().map(Selection::toGraphQlSpec)
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

        final SelectionSet that = (SelectionSet) other;
        return Objects.equals(this.getSelections(), that.getSelections());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSelections());
    }

    @Override
    public String toString() {
        return toGraphQlSpec();
    }

    /**
     * Returns an unmodifiable view of all {@link Selection}s in this {@link SelectionSet}.
     *
     * @return immutable list
     */
    private List<Selection> getSelections() {
        return selections;
    }
}
