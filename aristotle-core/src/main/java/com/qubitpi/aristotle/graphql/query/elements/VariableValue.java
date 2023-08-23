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
 * {@link VariableValue} models a variable name following a {@code $} sign.
 * <p>
 * According to GraphQL grammar (6.0)
 * <pre>
 * {@code
 * variable : '$' name;
 * name: NAME | FRAGMENT | QUERY | MUTATION | SUBSCRIPTION | SCHEMA | SCALAR | TYPE | INTERFACE | IMPLEMENTS | ENUM |
 * UNION | INPUT | EXTEND | DIRECTIVE;
 * }
 * </pre>
 * A variable is a string following a "$" sign.
 */
@Immutable
@ThreadSafe
public class VariableValue implements ValueWithVariable {

    /**
     * The part without "$" sign.
     */
    private final String name;

    /**
     * Private constructor.
     *
     * @param name  The variable name without "$" sign
     *
     * @throws NullPointerException if the {@code name} is {@code null}
     */
    private VariableValue(final String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

    /**
     * Creates a new {@link VariableValue} instance wrapping a specified variable name without "$" sign.
     *
     * @param name  The variable name without "$" sign
     *
     * @return a {@link ValueWithVariable} instance
     */
    public static ValueWithVariable of(final String name) {
        return new VariableValue(name);
    }

    @Override
    public String toGraphQlSpec() {
        // variable : '$' name;
        return String.format("$%s", getName());
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final VariableValue that = (VariableValue) other;
        return Objects.equals(this.getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return toGraphQlSpec();
    }

    /**
     * Returns the wrapped variable name.
     *
     * @return variable name
     */
    private String getName() {
        return name;
    }
}
