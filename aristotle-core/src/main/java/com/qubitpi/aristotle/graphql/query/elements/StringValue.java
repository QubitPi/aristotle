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
 * A {@link StringValue} is a string value that is quoted in its serialized form.
 * <p>
 * According to GraphQL grammar (6.0):
 * <pre>
 * {@code
 * StringValue: '"' (~(["\\\n\r\u2028\u2029])|EscapedChar)* '"';
 * }
 * </pre>
 * A {@link StringValue} is quoted.
 *
 * @see EnumValue
 */
@Immutable
@ThreadSafe
public class StringValue implements ValueWithVariable {

    /**
     * The part within the quote.
     */
    private final String value;

    /**
     * Private constructor.
     *
     * @param value  The string wrapped inside
     *
     * @throws NullPointerException if {@code value} is {@code null}
     */
    private StringValue(final String value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    /**
     * Creates a new {@link StringValue} instance wrapping a specified string.
     *
     * @param value  The provided string
     *
     * @return a {@link ValueWithVariable} instance
     */
    public static ValueWithVariable of(final String value) {
        return new StringValue(value);
    }

    @Override
    public String toGraphQlSpec() {
        // We quote it due to the GraphQL grammar for string stringValue:
        // StringValue: '"' (~(["\\\n\r\u2028\u2029])|EscapedChar)* '"';
        return String.format("\"%s\"", getValue());
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final StringValue that = (StringValue) other;
        return Objects.equals(this.getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return toGraphQlSpec();
    }

    /**
     * Returns the string wrapped inside this {@link StringValue}.
     *
     * @return actual string value
     */
    private String getValue() {
        return value;
    }
}
