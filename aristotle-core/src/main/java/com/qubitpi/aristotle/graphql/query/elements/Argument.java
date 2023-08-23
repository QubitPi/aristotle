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
 * {@link Argument} represents the same concept as {@link graphql.language.Argument GraphQL Argument} but specializes
 * in serialization, in contrast to {@link graphql.language.Argument GraphQL Argument}, which is designed for
 * deserialization.
 * <p>
 * According to GraphQL grammar
 * <pre>
 * {@code
 * argument : name ':' valueWithVariable;
 * }
 * </pre>
 * An {@link Argument} is a pair of argument name(TOKEN) and {@link ValueWithVariable argument value}.
 *
 * @see <a href="https://graphql.org/learn/queries/#arguments">GraphQL Arguments</a>
 */
@Immutable
@ThreadSafe
public class Argument {

    /**
     * The "name" TOKEN defined in GraphQL grammar.
     */
    private final String name;

    /**
     * A GraphQL concept that defines argument value.
     */
    private final ValueWithVariable value;

    /**
     * Private constructor.
     *
     * @param name  Argument name
     * @param value  Argument value
     *
     * @throws NullPointerException if any one of the arguments is {@code null}
     */
    private Argument(final String name, final ValueWithVariable value) {
        this.name = Objects.requireNonNull(name, "name");
        this.value = Objects.requireNonNull(value, "value");
    }

    /**
     * Constructs a new {@link Argument} with the specified argument name and value.
     *
     * @param name  Argument name
     * @param value  Argument value
     *
     * @return a new {@link Argument}
     */
    public static Argument of(final String name, final ValueWithVariable value) {
        return new Argument(name, value);
    }

    /**
     * Returns a GraphQL query string that representing an argument.
     *
     * @return a sub-string of a GraphQL query
     */
    public String toGraphQlSpec() {
        return String.format("%s: %s", getName(), getValue().toGraphQlSpec());
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final Argument argument = (Argument) other;
        return Objects.equals(this.getName(), argument.getName()) &&
                Objects.equals(this.getValue(), argument.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getValue());
    }

    @Override
    public String toString() {
        return toGraphQlSpec();
    }

    /**
     * Returns the name of this {@link Argument}.
     *
     * @return argument name
     */
    private String getName() {
        return name;
    }

    /**
     * Returns the value of this {@link Argument}.
     *
     * @return argument value
     */
    private ValueWithVariable getValue() {
        return value;
    }
}
