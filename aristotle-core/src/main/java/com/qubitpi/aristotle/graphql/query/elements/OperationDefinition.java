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
 * {@link OperationDefinition} represents the same concept as
 * {@link graphql.language.OperationDefinition GraphQL Operation Definition} but specializes in serialization, in
 * contrast to {@link graphql.language.OperationDefinition GraphQL Definition}, which is designed for deserialization.
 * <p>
 * According to GraphQL grammar (6.0)
 * <pre>
 * {@code
 *     definition:
 *         operationDefinition |
 *         fragmentDefinition |
 *         typeSystemDefinition
 *         ;
 *
 *     operationDefinition:
 *         selectionSet |
 *         operationType  name? variableDefinitions? directives? selectionSet;
 * }
 * </pre>
 * An {@link OperationDefinition} is a sub-type of {@link Definition} and has 2 sub-types:
 * <ol>
 *    <li> {@link SelectionSet}
 *    <li> {@link TypedOperation}
 * </ol>
 * <p>
 * This is a {@link java.util.function functional interface} whose functional method is {@link #toGraphQlSpec()}.
 *
 * @see Definition
 * @see SelectionSet
 * @see TypedOperation
 */
@FunctionalInterface
public interface OperationDefinition extends Definition {

    // intentionally left blank
}
