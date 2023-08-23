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

import java.io.Serializable;
import java.util.Objects;

/**
 * {@link TypedOperation} corresponds to the {@code operationType} defined in GraphQL grammar.
 *
 * According to GraphQL grammar:
 * <pre>
 * {@code
 * operationType  name? variableDefinitions? directives? selectionSet;
 *
 * operationType : SUBSCRIPTION | MUTATION | QUERY;
 * }
 * </pre>
 * A {@link TypedOperation} comes with required {@code operation type} and {@link SelectionSet} along with optional
 * operation name and variables used in query.
 */
public class TypedOperation implements OperationDefinition {

    /**
     * Models the required {@code operation type}.
     */
    public enum OperationType implements Serializable {

        /**
         * {@code operationType : SUBSCRIPTION} token.
         */
        SUBSCRIPTION("subscription"),

        /**
         * {@code operationType : MUTATION} token.
         */
        MUTATION("mutation"),

        /**
         * {@code operationType : QUERY} token.
         */
        QUERY("query");

        /**
         * An ANTLR token.
         */
        private final String name;

        /**
         * Constructor.
         *
         * @param name type name
         */
        OperationType(final String name) {
            this.name = Objects.requireNonNull(name, "name");
        }

        /**
         * Returns this token in string form.
         *
         * @return a sub-string of a GraphQL query
         */
        public String toGraphQlSpec() {
            return getName();
        }

        /**
         * Returns the type name shown in GraphQL query.
         *
         * @return GraphQL query type name
         */
        private String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = 5049217577677973567L;

    /**
     * Models the required {@code operation type}.
     */
    private final OperationType operationType;

    /**
     * The "name" TOKEN defined in GraphQL grammar.
     */
    private final String name;

    /**
     * Models the {@code variableDefinitions}.
     */
    private final VariableDefinitions variableDefinitions;

    /**
     * Models the required {@link SelectionSet}.
     */
    private final SelectionSet selectionSet;

    /**
     * Private constructor.
     *
     * @param operationType  One of the defined constants for GraphQL query type
     * @param name  Operation name
     * @param variableDefinitions  Variable specs in this GraphQL query
     * @param selectionSet  The fields involved in this query
     *
     * @throws NullPointerException if operation type or selections are {@code null}
     */
    private TypedOperation(
            final OperationType operationType,
            final String name,
            final VariableDefinitions variableDefinitions,
            final SelectionSet selectionSet
    ) {
        this.operationType = Objects.requireNonNull(operationType, "operationType");
        this.name = name;
        this.variableDefinitions = variableDefinitions;
        this.selectionSet = Objects.requireNonNull(selectionSet, "selectionSet");
    }

    /**
     * Creates a new {@link TypedOperation} that contains only an operation name and selections.
     *
     * @param operationType  One of the defined constants for GraphQL query type
     * @param selectionSet  The fields involved in this query
     *
     * @return a new instance
     */
    public static TypedOperation of(final OperationType operationType, final SelectionSet selectionSet) {
        return new TypedOperation(operationType, null, null, selectionSet);
    }

    /**
     * Creates a new {@link TypedOperation}.
     *
     * @param operationType  One of the defined constants for GraphQL query type
     * @param name  Operation name
     * @param variableDefinitions  Variable specs in this GraphQL query
     * @param selectionSet  The fields involved in this query
     *
     * @return a new instance
     */
    public static TypedOperation of(
            final OperationType operationType,
            final String name,
            final VariableDefinitions variableDefinitions,
            final SelectionSet selectionSet
    ) {
        return new TypedOperation(operationType, name, variableDefinitions, selectionSet);
    }

    @Override
    public String toGraphQlSpec() {
        return String.format(
                "%s %s%s%s",
                getOperationType().toGraphQlSpec(),
                getName() == null ? "" : getName(),
                getVariableDefinitions() == null
                        ? ""
                        : getVariableDefinitions().toGraphQlSpec() + " ",
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

        final TypedOperation that = (TypedOperation) other;
        return Objects.equals(this.getOperationType(), that.getOperationType()) &&
                Objects.equals(this.getName(), that.getName()) &&
                Objects.equals(this.getVariableDefinitions(), that.getVariableDefinitions()) &&
                Objects.equals(this.getSelectionSet(), that.getSelectionSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperationType(), getName(), getVariableDefinitions(), getSelectionSet());
    }

    @Override
    public String toString() {
        return toGraphQlSpec();
    }

    /**
     * Returns the type of this {@link TypedOperation}.
     *
     * @return operation type
     */
    private OperationType getOperationType() {
        return operationType;
    }

    /**
     * Returns the name of this {@link TypedOperation}.
     *
     * @return operation name
     */
    private String getName() {
        return name;
    }

    /**
     * Returns the variables defined in this {@link TypedOperation}.
     *
     * @return query variable
     */
    private VariableDefinitions getVariableDefinitions() {
        return variableDefinitions;
    }

    /**
     * Returns all the selected fields in this query.
     *
     * @return projections
     */
    private SelectionSet getSelectionSet() {
        return selectionSet;
    }
}
