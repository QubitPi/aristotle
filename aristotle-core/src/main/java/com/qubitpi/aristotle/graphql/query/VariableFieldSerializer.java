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
package com.qubitpi.aristotle.graphql.query;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * A Jackson serializer for String entity field.
 * <p>
 * {@link VariableFieldSerializer} serializes a String field differently when value can both represents a concrete value
 * or a GraphQL variable. On concrete value, it outputs the original value unmodified and quoted; on variable value, it
 * does not quote the original value.
 * <p>
 * For example, given the following entity:
 * <pre>
 * {@code
 * public class Book {
 *
 *     {@literal @}JsonSerialize(using = VariableFieldSerializer.class, as = String.class)
 *     private String title;
 * }
 * }
 * </pre>
 * A {@code Book(title="Java Concurrency in Practice")} serializes to
 * <pre>
 * {"title": "Java Concurrency in Practice"}
 * </pre>
 * However a {@code Book(title="$titlePassedByClient")} serializes to
 * <pre>
 * {"title": $titlePassedByClient}
 * </pre>
 * Note in the 1st serialization {@code title} value is quoted while the 2nd serialization it is not.
 * <p>
 * To serialize a String entity field in such a way, add the following annotation to the field, as shown above:
 * <pre>
 * {@code
 * {@literal @}JsonSerialize(using = VariableFieldSerializer.class, as = String.class)
 * }
 * </pre>
 *
 * @see <a href="https://graphql.org/learn/queries/#variables">Variables</a>
 */
public class VariableFieldSerializer extends JsonSerializer<String> {

    /**
     * GraphQL's starting character of a variable.
     */
    private static final String VARIABLE_SIGN = "$";

    @Override
    public void serialize(
            final String value,
            final JsonGenerator gen,
            final SerializerProvider serializers
    ) throws IOException {
        if (value.startsWith(VARIABLE_SIGN)) {
            // this is a variable
            gen.writeRawValue(value);
        } else {
            gen.writeString(value);
        }
    }
}
