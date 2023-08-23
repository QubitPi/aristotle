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

/**
 * {@link ValueWithVariable} models the {@code valueWithVariable} defined in GraphQL grammar.
 * <p>
 * GraphQL grammar(6.0) for {@code valueWithVariable} consists of sub-definitions:
 * <pre>
 * {@code
 *     valueWithVariable :
 *         variable |
 *         IntValue |
 *         FloatValue |
 *         StringValue |
 *         BooleanValue |
 *         NullValue |
 *         enumValue |
 *         arrayValueWithVariable |
 *         objectValueWithVariable;
 * }
 * </pre>
 * Each sub-definition is an implementation of {@link ValueWithVariable}.
 * <p>
 * This is a {@link java.util.function functional interface} whose functional method is {@link #toGraphQlSpec()}.
 */
@FunctionalInterface
public interface ValueWithVariable {

    /**
     * Returns the query string that corresponds to the valueWithVariable part of a GraphQL query.
     *
     * @return a sub-string of a GraphQL query
     */
    String toGraphQlSpec();
}
