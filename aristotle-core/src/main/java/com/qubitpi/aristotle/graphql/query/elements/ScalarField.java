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
 * A {@link ScalarField} represents the leaf of a GraphQL query.
 * <p>
 * According to GraphQL grammar (6.0):
 * <pre>
 * {@code
 * field : alias? name arguments? directives? selectionSet? = field : name
 * }
 * </pre>
 * A {@link ScalarField} is simply a un-quotes string value (name).
 *
 * @see Field
 */
@Immutable
@ThreadSafe
public class ScalarField implements Field {

    /**
     * The "name" TOKEN defined in GraphQL grammar.
     */
    private final String name;

    /**
     * Private constructor.
     *
     * @param name  The specified scalar field name
     */
    private ScalarField(final String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

    /**
     * Creates a new {@link ScalarField} instance with the specified scalar field name.
     *
     * @param name  The provided scalar field name
     *
     * @return a {@link Field}
     */
    public static Field of(final String name) {
        return new ScalarField(name);
    }

    @Override
    public String toGraphQlSpec() {
        return getName();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final ScalarField that = (ScalarField) other;
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
     * Returns the name of this {@link ScalarField} shown in the GraphQL query.
     *
     * @return field name
     */
    private String getName() {
        return name;
    }
}
