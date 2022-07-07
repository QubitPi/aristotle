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
package com.qubitpi.aristotle.graphql.querydsl.elements;

import jakarta.validation.constraints.NotNull;

/**
 * {@link Definition} represents the same concept as {@link graphql.language.Definition GraphQL Definition} but
 * specializes in serialization, in constrast to {@link graphql.language.Definition GraphQL Definition}, which is
 * designed for deserialization.
 * <p>
 * According to GraphQL grammar in
 * <a href="https://github.com/graphql-java/graphql-java/blob/master/src/main/antlr/Graphql.g4">graphql-java</a>:
 * <pre>
 * {@code
 *     definition:
 *         operationDefinition |
 *         fragmentDefinition |
 *         typeSystemDefinition |
 *         typeSystemExtension
 *         ;
 * }
 * </pre>
 * A {@link Definition} has 4 tub-types:
 * <ol>
 *     <li> operationDefinition
 *     <li> fragmentDefinition
 *     <li> typeSystemDefinition
 *     <li> typeSystemExtension
 * </ol>
 * This is a {@link java.util.function functional interface} whose functional method is {@link #toGraphQLSpec()}.
 */
@FunctionalInterface
public interface Definition {

    /**
     * Returns the query string that corresponds to a {@link graphql.language.Definition} part of a GraphQL query.
     *
     * @return a sub-string of a GraphQL query
     */
    @NotNull
    @SuppressWarnings("AbbreviationAsWordInName")
    String toGraphQLSpec();
}
