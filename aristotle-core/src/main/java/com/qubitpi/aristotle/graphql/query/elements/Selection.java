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
 * {@link Selection} represents the same concept as {@link graphql.language.Selection} but specializes in serialization,
 * in contrast to {@link graphql.language.Selection GraphQL Field Selection}, which is designed for deserialization.
 * <p>
 * According to GraphQL grammar (6.0)
 * <pre>
 * {@code
 * selection :
 *     field |
 *     fragmentSpread |
 *     inlineFragment;
 * }
 * </pre>
 * A {@link Selection} has 3 sub-types"
 * <ol>
 *     <li> {@link Field}
 *     <li> TODO - support fragmentSpread
 *     <li> TODO - support inlineFragment
 * </ol>
 *
 * This is a {@link java.util.function functional interface} whose functional method is {@link #toGraphQlSpec()}.
 *
 * @see Field
 */
public interface Selection {

    /**
     * Returns the query string that corresponds to a selection part of a GraphQL query.
     *
     * @return a sub-string of a GraphQL query
     */
    String toGraphQlSpec();
}
