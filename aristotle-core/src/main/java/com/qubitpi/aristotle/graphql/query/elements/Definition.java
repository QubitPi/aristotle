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
 * {@link Definition} represents the same concept as {@link graphql.language.Definition GraphQL Definition} but
 * specializes in serialization, in contrast to {@link graphql.language.Definition GraphQL Definition}, which is
 * designed for deserialization.
 * <p>
 * According to GraphQL grammar
 * <pre>
 * {@code
 *     definition:
 *         operationDefinition |
 *         fragmentDefinition |
 *         typeSystemDefinition
 *         ;
 * }
 * </pre>
 * A {@link Definition} has 3 sub-types:
 * <ol>
 *     <li> {@link OperationDefinition}
 *     <li> TODO - support fragmentDefinition interface
 *     <li> TODO - support typeSystemDefinition interface
 * </ol>
 *
 * <p>
 * This is a {@link java.util.function functional interface} whose functional method is {@link #toGraphQlSpec()}.
 *
 * @see OperationDefinition
 */
@FunctionalInterface
public interface Definition {

    /**
     * Returns the query string that corresponds to the a {@link graphql.language.Definition} part
     * of a GraphQL query.
     *
     * @return a sub-string of a GraphQL query
     */
    String toGraphQlSpec();
}
