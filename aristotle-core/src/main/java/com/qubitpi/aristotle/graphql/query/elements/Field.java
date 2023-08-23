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
 * {@link Field} represents the same concept as {@link graphql.language.Field GraphQL Field} but specializes in
 * serialization, in contrast to {@link graphql.language.Field GraphQL Field}, which is designed for deserialization.
 * <p>
 * According to GraphQL grammar (6.0):
 * <pre>
 * {@code
 *     selection :
 *         field |
 *         fragmentSpread |
 *         inlineFragment;
 *
 *     field : alias? name arguments? directives? selectionSet?;
 * }
 * </pre>
 * A {@link Field} is a sub-type of {@link Selection}. The simplest {@link Field} is a {@link ScalarField}; otherwise
 * it would be an {@link ObjectField}
 *
 * This is a {@link java.util.function functional interface} whose functional method is {@link #toGraphQlSpec()}.
 *
 * @see Selection
 * @see ScalarField
 * @see ObjectField
 * @see <a href="https://graphql.org/learn/schema/#scalar-types">Scalar types</a>
 * @see <a href="https://graphql.org/learn/schema/#object-types-and-fields">Object types and fields</a>
 */
@FunctionalInterface
public interface Field extends Selection {

    // intentionally left blank
}
