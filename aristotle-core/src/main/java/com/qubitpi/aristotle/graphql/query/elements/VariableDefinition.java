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
 * {@link VariableDefinition} represents the same concept as
 * {@link graphql.language.VariableDefinition GraphQL VariableDefinition} but specializes in serialization, in
 * contrast to {@link graphql.language.VariableDefinition GraphQL VariableDefinition}, which is designed for
 * deserialization.
 * <p>
 * According to GraphQL grammar,
 * <pre>
 * {@code
 * variableDefinition : variable ':' type defaultValue?;
 *
 * variable : '$' name;
 * }
 * </pre>
 * A {@link VariableDefinition} is a key-value pair of string and type literal with optional default value for that
 * variable.
 */
@Immutable
@ThreadSafe
public class VariableDefinition {

    /**
     * The "name" TOKEN defined in GraphQL grammar.
     * <p>
     * Note that there is no "$" in it.
     */
    private final String variable;

    /**
     * A simplified String representation of the aforementioned type literal.
     */
    private final String type;

    /**
     * Models the aforementioned default value.
     */
    private final ValueWithVariable defaultValue;

    /**
     * Private constructor.
     *
     * @param variable  The variable name without "$" sign
     * @param type  The type of this GraphQL variable
     * @param defaultValue  An optional default value for the {@code variable}
     *
     * @throws NullPointerException if {@code variable} or {@code type} is {@code null}
     */
    private VariableDefinition(final String variable, final String type, final ValueWithVariable defaultValue) {
        this.variable = Objects.requireNonNull(variable, "variable");
        this.type = Objects.requireNonNull(type, "type");
        this.defaultValue = defaultValue;
    }

    /**
     * Creates a new {@link VariableDefinition} instance without default value.
     *
     * @param variable  The variable name without "$" sign
     * @param type  The type of this GraphQL variable
     *
     * @return a new object
     */
    public static VariableDefinition of(
            final String variable,
            final String type
    ) {
        return new VariableDefinition(variable, type, null);
    }

    /**
     * Creates a new {@link VariableDefinition} instance with default value.
     *
     * @param variable  The variable name without "$" sign
     * @param type  The type of this GraphQL variable
     * @param defaultValue  An optional default value for the {@code variable}
     *
     * @return a new object
     */
    public static VariableDefinition of(
            final String variable,
            final String type,
            final ValueWithVariable defaultValue
    ) {
        return new VariableDefinition(variable, type, defaultValue);
    }

    /**
     * Returns the query string that corresponds to the a {@link graphql.language.VariableDefinition} part
     * of a GraphQL query.
     *
     * @return a sub-string of a GraphQL query
     */
    public String toGraphQlSpec() {
        return String.format(
                "$%s: %s%s",
                getVariable(),
                getType(),
                Objects.isNull(getDefaultValue())
                        ? ""
                        : String.format("=%s", getDefaultValue().toGraphQlSpec())
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

        final VariableDefinition that = (VariableDefinition) other;
        return Objects.equals(this.getVariable(), that.getVariable()) &&
                Objects.equals(this.getType(), that.getType()) &&
                Objects.equals(this.getDefaultValue(), that.getDefaultValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVariable(), getType(), getDefaultValue());
    }

    @Override
    public String toString() {
        return toGraphQlSpec();
    }

    /**
     * Returns the name of this variable.
     *
     * @return variable name
     */
    private String getVariable() {
        return variable;
    }

    /**
     * Returns the type of this variable.
     *
     * @return variable type
     */
    private String getType() {
        return type;
    }

    /**
     * Returns the default value, or {@code null} is none, of this variable.
     *
     * @return variable default value or {@code null}
     */
    private ValueWithVariable getDefaultValue() {
        return defaultValue;
    }
}
