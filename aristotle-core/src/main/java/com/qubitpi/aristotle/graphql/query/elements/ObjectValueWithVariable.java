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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.util.Objects;

/**
 * A simplified object that models a GraphQL object in a single string.
 * <p>
 * {@link ObjectValueWithVariable} implements the {@code objectValueWithVariable} defined in GraphQL grammar:
 * <pre>
 * {@code
 *     objectValueWithVariable: '{' objectFieldWithVariable* '}';
 *     objectFieldWithVariable : name ':' valueWithVariable;
 * }
 * </pre>
 * This simplified implementation models the {@code '{' objectFieldWithVariable* '}'} as plain Java {@link Object},
 * which must be Jackson-serializable.
 *
 * @see ValueWithVariable
 */
@Immutable
@ThreadSafe
public class ObjectValueWithVariable implements ValueWithVariable {

    /**
     * GraphQL argument name is unquoted; hence quoted field is disabled.
     */
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
            .configure(
                    JsonGenerator.Feature.QUOTE_FIELD_NAMES,
                    false
            )
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setSerializationInclusion(JsonInclude.Include.NON_DEFAULT)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

    /**
     * Models the {@code '{' objectFieldWithVariable* '}'}, which is obtained by Jackson-serializing itself.
     */
    private final Object object;

    /**
     * Private constructor.
     *
     * @param object  A provided Jackson-serializable object
     *
     * @throws NullPointerException if the {@code object} is {@code null}
     */
    private ObjectValueWithVariable(final Object object) {
        this.object = Objects.requireNonNull(object, "object");
    }

    /**
     * Creates a new {@link ObjectValueWithVariable} instance wrapping a specified Jackson-serializable {@link Object}.
     *
     * @param object  A provided Jackson-serializable object
     *
     * @return {@link ValueWithVariable} wrapping an {@link Object} value.
     */
    public static ValueWithVariable of(final Object object) {
        return new ObjectValueWithVariable(object);
    }

    @Override
    public String toGraphQlSpec() {
        try {
            return JSON_MAPPER.writeValueAsString(getObject());
        } catch (final JsonProcessingException exception) {
            throw new IllegalStateException(exception);
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final ObjectValueWithVariable that = (ObjectValueWithVariable) other;
        return Objects.equals(this.getObject(), that.getObject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getObject());
    }

    @Override
    public String toString() {
        return toGraphQlSpec();
    }

    /**
     * Returns the wrapped object.
     *
     * @return wrapped object
     */
    private Object getObject() {
        return object;
    }
}
