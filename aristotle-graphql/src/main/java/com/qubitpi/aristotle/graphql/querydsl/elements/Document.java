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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A GraphQL {@link Document} describes a complete file or request string operated on by a GraphQL service or client.
 * <p>
 * A document contains multiple definitions, either executable or representative of a GraphQL type system.
 * <p>
 * Documents are only executable by a GraphQL service if they are {@code ExecutableDocument} and contain at least one
 * {@code OperationDefinition}. A Document which contains {@code TypeSystemDefinitionOrExtension} must not be executed;
 * GraphQL execution services which receive a Document containing these should return a descriptive error.
 * <p>
 * GraphQL services which only seek to execute GraphQL requests and not construct a new GraphQL schema may choose to
 * only permit {@code ExecutableDocument}
 * <p>
 * Documents which do not contain {@code OperationDefinition} or do contain {@code TypeSystemDefinitionOrExtension} may
 * still be parsed and validated to allow client tools to represent many GraphQL uses which may appear across many
 * individual files.
 * <p>
 * If a Document contains only one operation, that operation may be unnamed. If that operation is a query without
 * variables or directives then it may also be represented in the shorthand form, omitting both the {@code query}
 * keyword as well as the operation name. Otherwise, if a GraphQL Document contains multiple operations, each operation
 * must be named. When submitting a Document with multiple operations to a GraphQL service, then name of the desired
 * operation to be executed must also be provided.
 * <p>
 * Aristotle's {@link Document} represents the same concept as {@link graphql.language.Document GraphQL Document} but
 * specialized in serialization, in contrast to {@link graphql.language.Document GraphQL Document}, which is designed
 * for deserialization.
 * <p>
 * According to GraphQL grammar in
 * <a href="https://github.com/graphql-java/graphql-java/blob/master/src/main/antlr/Graphql.g4">graphql-java</a>:
 * <pre>
 * {@code
 * document : definition+;
 * }
 * </pre>
 * A {@link Document} is a list of one or more {@link Definition}s
 */
public class Document {

    private final List<Definition> definitions;

    /**
     * Constructor.
     *
     * @param definitions  A list containing {@code OperationDefinition}s or {@code TypeSystemDefinitionOrExtension}s
     *
     * @throws NullPointerException if {@code definitions} is {@code null}
     */
    public Document(final @NotNull List<Definition> definitions) {
        this.definitions = new ArrayList<>(Objects.requireNonNull(definitions, "definitions")); // defensive copy
    }

    /**
     * Returns the complete GraphQL query that this {@link Document} represents.
     *
     * @return a string representation of a GraphQL query
     */
    @NotNull
    public String toQuery() {
        return getDefinitions().stream()
                .map(Definition::toGraphQLSpec)
                .collect(Collectors.joining(" "));
    }

    @Override
    public String toString() {
        return toQuery();
    }

    @NotNull
    private List<Definition> getDefinitions() {
        return definitions;
    }
}
