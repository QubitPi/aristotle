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
 * A {@link EnumValue} is a string value that is not quoted in its serialized form.
 * <p>
 * {@link EnumValue} maps to the same concept of {@link graphql.language.EnumValue GraphQL EnumValue} as defined below
 * <pre>
 * {@code
 * enumValue : name ;
 * }
 * </pre>
 * {@link EnumValue} specializes in serialization, in contrast to {@link graphql.language.EnumValue GraphQL EnumValue}
 * which is designed for deserialization.
 *
 * @see StringValue
 */
@Immutable
@ThreadSafe
public class EnumValue implements ValueWithVariable {

    /**
     * The "name" TOKEN defined in GraphQL grammar.
     */
    private final String value;

    /**
     * Private constructor.
     *
     * @param value  The wrapped string
     *
     * @throws NullPointerException if the {@code value} is {@code null}
     */
    private EnumValue(final String value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    /**
     * Creates a new string wrapper.
     * <p>
     * The string will be un-quoted in serialization.
     *
     * @param value  The specified string
     *
     * @return string wrapper as a GraphQL enum
     */
    public static ValueWithVariable of(final String value) {
        return new EnumValue(value);
    }

    @Override
    public String toGraphQlSpec() {
        return getValue();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final EnumValue that = (EnumValue) other;
        return Objects.equals(this.getValue(), that.getValue());
    }

    @Override
    public String toString() {
        return toGraphQlSpec();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    /**
     * Returns the wrapped enum as string.
     *
     * @return GraphQL enum
     */
    private String getValue() {
        return value;
    }
}
